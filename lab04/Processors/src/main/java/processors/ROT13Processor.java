package processors;

import processing.Processor;
import processing.StatusListener;
import processors.worker.ProcessWorker;
import processors.worker.Worker;

public class ROT13Processor implements Processor, Worker {
    private String taskToProcess;
    private String result = "";

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        this.taskToProcess = s;

        return ProcessWorker.runTask(this, statusListener);
    }

    @Override
    public String getInfo() {
        return "ROT13 #tekst #tryb | tryb: [koduj, dekoduj]";
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public void work() {
        String[] split =  this.taskToProcess.split("#");
        String text = split[1].trim();
        String mode =  split[2].substring(0,  split[2].indexOf('|')).trim();

        this.result = switch (mode) {
            case "koduj", "dekoduj" -> encode(text);
            default -> "";
        };
    }

    private String encode(String text) {
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char c : chars) {
            if (c >= 'a' && c <= 'z') {
                c = (char) ('a' + (c - 'a' + 13 + 26) % 26);
            } else if (c >= 'A' && c <= 'Z') {
                c = (char) ('A' + (c - 'A' + 13 + 26) % 26);
            }

            sb.append(c);
        }

        return sb.toString();
    }
}
