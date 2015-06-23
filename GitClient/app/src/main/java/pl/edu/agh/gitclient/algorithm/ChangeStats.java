package pl.edu.agh.gitclient.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ChangeStats {

    private String name;
    private ElementType elementType;
    private int linesAdded;
    private int linesRemoved;
    private List<ChangeStats> childs;

    public ChangeStats(String name, ElementType elementType, boolean lineAdded){
        this.name = name;
        this.elementType = elementType;

        childs = new ArrayList<ChangeStats>();

        if(lineAdded){
            linesAdded++;
        }
        else{
            linesRemoved++;
        }
    }

    public void addChild(List<FileElement> path, boolean added){
        path.remove(0);

        if(added){
            linesAdded++;
        }
        else{
            linesRemoved++;
        }

        if(path.size() > 0){


            for(int i = 0; i < childs.size(); i++){
                if(childs.get(i).getName().equals(path.get(0).getName()) && childs.get(i).getElementType().equals(path.get(0).getElementType())){
                    childs.get(i).addChild(path, added);
                    return;
                }
            }


            ChangeStats changeStats = new ChangeStats(path.get(0).getName(), path.get(0).getElementType(), added);
            if(path.size() > 1){
                changeStats.addChild(path, added);
            }

            childs.add(changeStats);

        }
    }
    public String getName() {
        return name;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public List<ChangeStats> getChilds() {
        return childs;
    }

    public String toString(){
        String stringValue = elementType + " " + name + " (+" + linesAdded + ", -" + linesRemoved + ")";


        for(int i = 0; i < childs.size(); i++){
            stringValue += "\n\t" + childs.get(i).toString();
        }



        return stringValue;
    }

    public void UpdateValue(boolean added){
        if(added){
            linesAdded++;
        }
        else{
            linesRemoved++;
        }
    }

    public String getStatsString(){
        String stats = "";

        for(int i = 0; i < childs.size(); i++){
            stats += childs.get(i).getStatsString(1);
            if(i < childs.size() - 1){
                stats += "\n";
            }
        }

        return stats;
    }

    public String getStatsString(int level){
        String stringValue = elementType + " " + name + " (+" + linesAdded + ", -" + linesRemoved + ")";


        for(int i = 0; i < childs.size(); i++){
            stringValue += "\n";

            for(int j = 0; j < level; j++){
                stringValue += "\t";
            }

            stringValue += childs.get(i).getStatsString(level + 1);
        }



        return stringValue;
    }

}
