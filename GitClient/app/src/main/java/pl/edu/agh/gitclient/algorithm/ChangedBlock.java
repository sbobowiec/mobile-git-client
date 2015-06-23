package pl.edu.agh.gitclient.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ChangedBlock {

    private int start;
    private int end;

    private String path;

    private List<String> content;

    public ChangedBlock(int lineNumber, String content){
        this.start = lineNumber;
        this.end = lineNumber;

        this.content = new ArrayList<>();

        this.content.add(content);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getContent() {
        return content;
    }

    public void AddContent(ChangedBlock block){
        if(block.getContent().size() > 0){
            this.content.add(block.getContent().get(0));
        }

        if(end < block.getEnd()){
            end = block.getEnd();
        }

    }

    public String toString(){
        String change = path;

        for(int i = 0; i < content.size(); i++){
            change += "\n*\t" + content.get(i);
        }

        return change;
    }

}
