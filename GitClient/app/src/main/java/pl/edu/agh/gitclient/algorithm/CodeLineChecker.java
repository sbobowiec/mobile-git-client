package pl.edu.agh.gitclient.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CodeLineChecker {

    private List<String> fileContent;
    //   private List<String> cleanFileContnent;
    private FileElement root;
    //   private List<List<String>> splitedContet;
    private List<ChangedBlock> changes;

    private int offset;

    public CodeLineChecker(List<String> fileContent){
        offset = 0;

        //    roots = new ArrayList<FileElement>();

        this.fileContent = fileContent;
        this.changes = new ArrayList<ChangedBlock>();

        /*this.splitedContet = new ArrayList<List<String>>();
        splitedContet.add(new ArrayList<String>());*/

        for(int i = 0; i < fileContent.size(); i++){
            if((fileContent.get(i).startsWith("-") || fileContent.get(i).startsWith("+")) && !fileContent.get(i).startsWith("---") && !fileContent.get(i).startsWith("+++")){
                changes.add(new ChangedBlock(i, fileContent.get(i)));
            }
        }

        List<String> cleanFileContent = removeComments(fileContent);

      /*  for(int i = 0; i < cleanFileContent.size(); i++){
            if(cleanFileContent.get(i).startsWith("diff") || cleanFileContent.get(i).startsWith("+++")){
                if(i + 1 < cleanFileContent.size() && fileContent.get(i + 1).startsWith("+++")){
                    continue;
                }
                else{
                    //roots.add(new FileElement(ElementType.FILE, cleanFileContent.get(i), i, countLinesRange(cleanFileContent, i)));
                    splitedContet.add(new ArrayList<String>());
                }
            }
                splitedContet.get(splitedContet.size() - 1).add(cleanFileContent.get(i));

        }*/

        root = buildElementsTree(cleanFileContent);



        //     this.rootElement = buildElementsTree(cleanFileContent);
    }

    public List<ChangedBlock> checkLines() {

        for(int i = 0; i < changes.size(); i++){
            String path =  "";//rootElement.getLinePath(changes.get(i).getStart());


            path = root.getLinePath(changes.get(i).getStart());


            changes.get(i).setPath(path);
            //    System.out.println(path);
        }

        List<ChangedBlock> compressed = compressChanges(changes);
        return compressed;
    }

    public List<ChangeStats> CalculateStats(){
        List<ChangeStats> stats = new ArrayList<ChangeStats>();

        //    List<FileElement> path = null;

        for(int i = 0; i < changes.size(); i++){
            List<FileElement> path = null;

            boolean lineAdded = false;
            if(changes.get(i).getContent().get(0).startsWith("+")){
                lineAdded = true;
            }

            path = root.getElementsPath(changes.get(i).getStart());


            if(path != null && path.size() > 0){
            /*if(path.size() == 1){*/

                boolean found = false;


                for(int j = 0; j < stats.size(); j++){
                    if(stats.get(j).getName().equals(path.get(0).getName())
                            && stats.get(j).getElementType() == path.get(0).getElementType()){
                        stats.get(j).addChild(path, lineAdded);
                        found = true;
                        break;
                    }
                }

                /*if(stats.size() > 0 && stats.get(stats.size() - 1).getName().equals(path.get(0).getName()) &&
                        stats.get(stats.size() - 1).getElementType() == path.get(0).getElementType()){
                    stats.get(stats.size() - 1).UpdateValue(lineAdded);
                }*/
                if(!found){
                    stats.add(new ChangeStats(path.get(0).getName(), path.get(0).getElementType(), lineAdded));
                }

            }
            /*else if (path.size() > 1){
                stats.get(stats.size() - 1).addChild(path, lineAdded);
            }
            }*/
        }


        return stats;
    }

    private List<ChangedBlock> compressChanges(List<ChangedBlock> changes){
        List<ChangedBlock> compressed = new ArrayList<ChangedBlock>();

        for(int i = 0; i < changes.size(); i++){
            if(compressed.size() > 0){
                if(compressed.get(compressed.size() - 1).getPath().equals(changes.get(i).getPath())){
                    compressed.get(compressed.size() - 1).AddContent(changes.get(i));
                }
                else{
                    compressed.add(changes.get(i));
                }
            }
            else{
                compressed.add(changes.get(i));
            }
        }

        return compressed;
    }

    private FileElement buildElementsTree(List<String> fileContent) {

        String fileName = "";
       /* for(int i = 0; i < fileContent.size(); i++){
            if(fileContent.get(i).startsWith("+++") || fileContent.get(i).startsWith("---")){
                fileName = fileContent.get(i);
                break;
            }
        }

        FileElement fileRoot = new FileElement(ElementType.FILE, fileName, 0 + offset, fileContent.size() - 1 + offset);
*/

        FileElement fileRoot = new FileElement(ElementType.OTHER, "DIFF FILE", 0, fileContent.size() - 1);
        String className = "";
        for (int i = 0; i < fileContent.size(); i++) {
            String lineTmp = fileContent.get(i);

            ElementType type = recognizeType(lineTmp, className);
            String name = recognizeName(lineTmp, type);
            int endLine = countLinesRange(fileContent, i, type);

            switch (type){
                case FILE:
                    break;
                case CLASS:
                    className = name;
                    //   System.out.println( (i + offset) + "-" + (endLine + offset) + "\t" + type + "\t" + lineTmp.trim());
                    break;
                case ENUM:
                    //        System.out.println( i + "\t" + type + "\t" + lineTmp.trim());
                    break;
                case CONSTRUCTOR:

                    //      System.out.println( i + "\t" + type + "\t" + lineTmp.trim());
                    break;
                case METHOD:
                    //        System.out.println( i + "\t" + type + "\t" + lineTmp.trim());
                    break;
                case OTHER:
                    break;
            }

            if(type == ElementType.FILE || type == ElementType.CLASS || type == ElementType.ENUM || type == ElementType.METHOD || type == ElementType.CONSTRUCTOR){
                // String name = recognizeName(lineTmp, type);
                //   System.out.println();


                fileRoot.addElement(new FileElement(type, name, i, endLine));
                //  FileElement element = new FileElement(type)
            }

            //    offset++;

        }

        return fileRoot;
    }

    private int countLinesRange(List<String> fileContent, int start, ElementType type){

        if(type == ElementType.FILE){
            int fileCounter;
            for(fileCounter = start + 1; fileCounter < fileContent.size(); fileCounter++){
                if(fileContent.get(fileCounter).startsWith("diff ")){
                    break;
                }
            }
            return fileCounter - 1;
        }

        int counter = 0;
        boolean startFound = false;

        //     int end = 0;

        for (int i = start; i < fileContent.size(); i++){
            String line = fileContent.get(i);
            if(line.startsWith("diff")){
                return i - 1;
            }
            for(int j = 0; j < line.length(); j++){
                if(line.charAt(j) == '{'){
                    startFound = true;
                    counter++;
                }
                else if(line.charAt(j) == '}'){
                    counter--;
                }
                if (counter == 0 && startFound){
                    return  i;
                }
            }
        }

        return fileContent.size() - 1;
    }

    private String recognizeName(String line, ElementType type){
        String name = "";

        if(type == ElementType.FILE){
            name = line.substring(line.lastIndexOf(" ") + 1);
            name = name.substring(name.indexOf("/"));
            return name;
        }

        if(type == ElementType.CLASS || type == ElementType.ENUM){
            int index = line.indexOf(" {");

            if(index < 0){
                index = line.indexOf('{');
            }

            int findDelay = 0;

            if(line.contains(" extends ")){
                findDelay += 2;
            }
            if(line.contains(" implements ")){
                findDelay += 2;
            }
            if(line.contains(" : ")){
                findDelay += 2;
            }

            for(int i = index - 1; i >= 0; i--){
                if(line.charAt(i) == ' '){
                    if(findDelay > 0){
                        name = ' ' + name;
                        findDelay--;
                        continue;
                    }

                    break;
                }
                name = line.charAt(i) + name;
            }
        }

        if(type == ElementType.METHOD || type == ElementType.CONSTRUCTOR){
            int index = line.indexOf(" (");

            if(index < 0){
                index = line.indexOf('(');
            }

            for(int i = index - 1; i >= 0; i--){
                if(line.charAt(i) == ' '){
                    break;
                }
                name = line.charAt(i) + name;
            }

        }

        return name;
    }

    private ElementType recognizeType(String line, String className){
        if(line.startsWith("diff ") || line.startsWith(" diff")){
            return ElementType.FILE;
        }
        if (line.startsWith("class ") || line.contains(" class ") && !line.contains(".")){
            return ElementType.CLASS;
        }
        if (line.startsWith("enum ") || line.contains(" enum ") && !line.contains(".")){
            return ElementType.ENUM;
        }
        if (line.contains("(") && line.contains(")") && !line.contains(";") && !line.contains(".")
                && !(line.contains("if(") || line.contains("if ")
                || line.contains("switch ") || line.contains("switch(")
                || line.contains("while ") || line.contains("while(")
                || line.contains("for ") || line.contains("for("))){

            String name = recognizeName(line, ElementType.CONSTRUCTOR);

            if(name.equals(className)){
                return ElementType.CONSTRUCTOR;
            }
            return ElementType.METHOD;
        }


        return ElementType.OTHER;
    }

    private void PrintFileContent(List<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            System.out.println(i + 1 + "\t" + fileContent.get(i));
        }
    }

    private List<FileElement> detectElements(List<String> fileContent) {
        List<FileElement> elements = new ArrayList<FileElement>();


        return elements;
    }


    private List<String> removeComments(List<String> fileContent) {
        List<String> cleanFile = new ArrayList<String>();


        boolean inCommentBlock = false;

        for (int i = 0; i < fileContent.size(); i++) {
            String line = "";
            String lineTmp = fileContent.get(i);

            for (int j = 0; j < lineTmp.length(); j++) {
                if (inCommentBlock) {
                    if (j < lineTmp.length() - 1 && lineTmp.charAt(j) == '*' && lineTmp.charAt(j + 1) == '/') {
                        inCommentBlock = false;
                        j++;

                    }
                    continue;
                } else {
                    if (j < lineTmp.length() - 1 && lineTmp.charAt(j) == '/' && lineTmp.charAt(j + 1) == '/') {
                        //  clearFile.add(line);
                        //   j++;
                        break;
                    }
                    if (j < lineTmp.length() - 1 && lineTmp.charAt(j) == '/' && lineTmp.charAt(j + 1) == '*') {
                        inCommentBlock = true;
                        //  j++;
                        continue;
                    }
                    line += lineTmp.charAt(j);
                }
            }

            cleanFile.add(line);


        }

        return cleanFile;
    }


    public static void main(String[] args) {
        List<String> fileContent = FileLoader.LoadFile("testFile.txt");

        CodeLineChecker codeLineChecker = new CodeLineChecker(fileContent);


       /* List<ChangedBlock> changes = codeLineChecker.checkLines();

        for(int i = 0; i < changes.size(); i++){
            System.out.println(changes.get(i));
        }*/

        List<ChangeStats> stats = codeLineChecker.CalculateStats();

        System.out.println(stats.get(0).getStatsString());

    }
}

