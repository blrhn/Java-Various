cmake_minimum_required(VERSION 4.2)
project(lab07lib)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

set(CMAKE_CXX_STANDARD 20)

add_library(lab07lib SHARED jni_filter_ConvolutionEngine.cpp
        jni_filter_ConvolutionEngine.h)
