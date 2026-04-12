package processors;

import processing.Processor;
import processing.StatusListener;
import processors.worker.ProcessWorker;
import processors.worker.Worker;

public class CaesarProcessor implements Processor, Worker {
    private String taskToProcess;
    private String result = "";

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        this.taskToProcess = s;

        return ProcessWorker.runTask(this, statusListener);
    }

    @Override
    public String getInfo() {
        return "Cezar #tekst #offset #tryb | tryb: [koduj, dekoduj]";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void work() {
        String[] split = this.taskToProcess.split("#");
        String text = split[1].trim();
        int offset = Integer.parseInt(split[2].trim());
        String mode =  split[3].substring(0,  split[3].indexOf('|')).trim();

        this.result = switch (mode) {
            case "koduj" -> encode(text, offset);
            case "dekoduj" -> decode(text, offset);
            default -> "";
        };
    }

    private String encode(String text, int offset) {
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char c : chars) {
            if (c != ' ') {
                int ascii = c - 'a';
                int newAscii = (ascii + offset) % 26;
                sb.append((char)('a' + newAscii));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private String decode(String text, int offset) {
        return encode(text, 26 - (offset % 26));
    }
}
