package pl.edu.agh.gitclient.algorithm;

import java.util.ArrayList;
import java.util.List;

public class FileElement {

    private int startLine;
    private int endLine;
    private ElementType elementType;
    private String name;
    private List<FileElement> childs;

    public FileElement(ElementType elementType, String name, int startLine, int endLine){
        this.elementType = elementType;

        if(name.startsWith("+++") || name.startsWith("---")){
            this.name = name.substring(4);
        }
        else{
            this.name = name;
        }

        this.startLine = startLine;
        this.endLine = endLine;

        childs = new ArrayList<>();
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    private boolean containLine(int lineNumber){
        if (lineNumber >= startLine && lineNumber <= endLine){
            return true;
        }
        return false;
    }

    public String getLinePath(int lineNumber){
        if(containLine(lineNumber)){
            String pathFromChilds = "";
            for(int i = 0; i < childs.size(); i++){
                pathFromChilds += childs.get(i).getLinePath(lineNumber);
            }

            if(!pathFromChilds.equals("")){
                pathFromChilds = " => " + pathFromChilds;
            }

           /* if(elementType == ElementType.FILE){
                return "in " + elementType + pathFromChilds;
            }*/

            return "in " + elementType + " \"" + name + "\"" + pathFromChilds;
        }
        return "";
    }

    public boolean containElement(FileElement element){
        if (element.getStartLine() >= startLine && element.getEndLine() <= endLine){
            return true;
        }
        return false;
    }

    public boolean addElement(FileElement element){
        if(containElement(element)){
            boolean added = false;
            for (int i = 0; i < childs.size(); i++){
                if(childs.get(i).addElement(element)){
                    added = true;
                    break;
                }
            }

            if(!added){
                childs.add(element);

                List<FileElement> elementsToRemove = new ArrayList<FileElement>();
                for(int i = 0; i < childs.size() - 1; i++){
                    if(element.addElement(childs.get(i))){
                        elementsToRemove.add(childs.get(i));
                    }
                }

                for(int i = 0; i < elementsToRemove.size(); i++){
                    childs.remove(elementsToRemove.get(i));
                }
            }

            return true;
        }
        return false;
    }

}
