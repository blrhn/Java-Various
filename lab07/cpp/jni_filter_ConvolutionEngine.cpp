#include "jni_filter_ConvolutionEngine.h"

#include <iostream>

int clamp(int value, int min, int max) {
  int tempMax = (value >= min) ? value : min;

  return (max <= tempMax) ? max : tempMax;
}

JNIEXPORT jintArray JNICALL Java_jni_filter_ConvolutionEngine_convolveNative
  (JNIEnv *env, jobject obj, jintArray img, jint imageWidth, jint imageHeight, jintArray krn, jint kernelWidth, jint kernelHeight) {

  // pozyskanie wskaznika do elementow tablicy
  // wynik getintarrayelements wskazuje na nonmovable tablice intow
  // jni "pins" down tablice albo tworzy kopie tablice into nonmovable memory
  // dlatego trzeba potem wywolac releaseintarrayelements, bo jak sie nie wywola to
  // jvm nie moze reclaim the memory used to store the nonovable copy of the array

  // Because the
  // underlying garbage collector may not support pinning, the virtual machine may
  // return a pointer to a copy of the original primitive array.

  // get<typ>arrayelement moze potencjalnie skopiowac cala tablice
  // jesli chcemy ograniczyc liczbe elementow, ktore sa kopiowane lub
  // interesuje nas mala liczba elementow w (potencjalnie) duzej tablicy
  // to nalezy uzyc get/set<typ>arrayregion - one pozwalaja (poprzez kopiowanie)
  // na dostep do mniejszego setu elementow w tablicy
  jint *image = env->GetIntArrayElements(img, nullptr);
  jint *kernel = env->GetIntArrayElements(krn, nullptr);

  jintArray cnvl = env->NewIntArray(imageWidth * imageHeight);
  jint *convolved = env->GetIntArrayElements(cnvl, nullptr);

  if (image == nullptr || kernel == nullptr || convolved == nullptr) {
    if (image) env->ReleaseIntArrayElements(img, image, JNI_ABORT);
    if (kernel) env->ReleaseIntArrayElements(krn, kernel, JNI_ABORT);
    if (convolved) env->ReleaseIntArrayElements(cnvl, convolved, JNI_ABORT);

    return nullptr;
  }

  int offsetX = kernelWidth / 2;
  int offsetY = kernelHeight / 2;

  for (int i = 0; i < imageHeight; i++) {
    for (int j = 0; j < imageWidth; j++) {
      jint sum = 0;

      for (int k = 0; k < kernelHeight; k++) {
        for (int l = 0; l < kernelWidth; l++) {
          int sX = clamp(j + l - offsetX, 0 , imageWidth - 1);
          int sY = clamp(i + k - offsetY, 0, imageHeight - 1);

          sum += image[sY * imageWidth + sX] * kernel[k * kernelWidth + l];
        }
      }

      convolved[i *  imageWidth + j] = sum;
    }
  }

  // releaseintarrayelements pozwala jni na kopiowanie z powrotem i zwalniane pamieci referenced
  // przez elems jesli jest to kopia original java array
  // "copy back" pozwala programowi wywolujacemu na pobranie nowych wartosci tablicy, ktora metoda
  // natywna zedytowala
  // ie: releaseintarrayelements will unpin the memory
  env->ReleaseIntArrayElements(img, image, JNI_ABORT);
  env->ReleaseIntArrayElements(krn, kernel, JNI_ABORT);
  env->ReleaseIntArrayElements(cnvl, convolved, 0);

  // 0 - kopiowanie elementow z powrotem i uwolnienie bufora
  // jni_commit - kopiowanie elementow z powrotem ale nie zwalnia bufora
  // jni_abort - zwolnienie buforu bez kopiowania zmian

  return cnvl;
}

// get/set<type>arrayregion - Copies the contents of primitive arrays to or from a preallocated C buffer.
// get<type>arrayelements - Obtains a pointer to the contents of a primitive array. May return a copy of the array.

// kopiowanie do/z preallocated C buffer, małe, fixed sizes arrays - get/set<type>arrayregion (C buffer moze
// byc easily allocated na stosie very cheaply). overhead na kopiowanie jest pomijalny

// jesli nie mamy preallocated C buffer, tablica nie ma okreslonego rozmiaru, kod natywny
// nie wykonuje blocking calls podczas przetrzymywania wskaznika - get/releaseprimitivearraycritical
// a jesli ma blocking calls to get/release<type>arrayelements

// It is always safe to use the Get/Release<type>ArrayElements family of
// functions. The virtual machine either returns a direct pointer to the array elements,
// or returns a buffer that holds a copy of the array elements.


JNIEXPORT jintArray JNICALL Java_jni_filter_ConvolutionEngine_convolveNativeDiff
  (JNIEnv *env, jobject obj, jintArray img, jint imageWidth, jint imageHeight, jintArray krn, jint kernelWidth, jint kernelHeight) {

  jint *image = env->GetIntArrayElements(img, nullptr);
  jint *kernel = env->GetIntArrayElements(krn, nullptr);

  if (image == nullptr || kernel == nullptr) {
    if (image) env->ReleaseIntArrayElements(img, image, JNI_ABORT);
    if (kernel) env->ReleaseIntArrayElements(krn, kernel, JNI_ABORT);

    return nullptr;
  }

  jint *convolved = new jint[imageWidth * imageHeight];

  int offsetX = kernelWidth / 2;
  int offsetY = kernelHeight / 2;

  for (int i = 0; i < imageHeight; i++) {
    for (int j = 0; j < imageWidth; j++) {
      jint sum = 0;

      for (int k = 0; k < kernelHeight; k++) {
        for (int l = 0; l < kernelWidth; l++) {
          int sX = clamp(j + l - offsetX, 0 , imageWidth - 1);
          int sY = clamp(i + k - offsetY, 0, imageHeight - 1);

          sum += image[sY * imageWidth + sX] * kernel[k * kernelWidth + l];
        }
      }

      convolved[i *  imageWidth + j] = sum;
    }
  }

  jintArray cnvl = env->NewIntArray(imageWidth * imageHeight);
  env->SetIntArrayRegion(cnvl, 0, imageWidth * imageHeight, convolved);

  env->ReleaseIntArrayElements(img, image, JNI_ABORT);
  env->ReleaseIntArrayElements(krn, kernel, JNI_ABORT);
  delete[] convolved;

  return cnvl;
}