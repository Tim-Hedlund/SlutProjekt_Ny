import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileMaster {
    File file;

    //File master constructor, sets the "file" to input file
    FileMaster(File file) {
        this.file = file;
    }

    //Returns an arraylist of Strings when scanning a document, Only returns one paragraph at a time as the list.
    ArrayList<String> returnParagraphStringList (int paragraphNum) {

        ArrayList<String> returnList = new ArrayList<>();

        try{
            Scanner fileReader = new Scanner(this.file);
            int rowNum = getParagraphRow(paragraphNum);

            skipLines(fileReader, rowNum);
            String currentLine;

            while (true) {

                try {
                    currentLine = fileReader.nextLine();
                } catch (Exception e){
                    break;
                }

                //checks if line is not empty. Important because empty lines are the end of the paragraph
                if (currentLine.length() != 0) {

                    returnList.add(currentLine);

                } else {
                    break;
                }

            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
        }

        return returnList;

    }

    //Gets how many paragraphs are in a document
    int getFileParagraphCount() {
        try {
            Scanner fileReader = new Scanner(this.file);

            int paragraphCount = 0;
            boolean isNewParagraph = true;

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                //checks if the line is empty (new paragraph) if the line is empty it sets isNewParagraph to true
                //also allows infinite empty lines between paragraphs
                if (line.isEmpty()) {

                    if (isNewParagraph) {

                        paragraphCount++;
                        isNewParagraph = false;

                    }

                } else {

                    isNewParagraph = true;

                }
            }

            if (isNewParagraph) {
                paragraphCount++;
            }

            fileReader.close();

            return paragraphCount;
        } catch (FileNotFoundException e) {

            System.out.println("Error: File not found,");
            e.printStackTrace();
            return -1;

        }
    }

    //both this and the method below were never used but were going to be important for other parts of the game
    String returnLine (int lineNumber) { //returnerar värdet vid en line av en fil

        try{
            Scanner fileReader = new Scanner(this.file);

            for (int i = 0; i < lineNumber - 1; i++) {
                if (!fileReader.hasNextLine()) {
                    System.out.println("Error, ");
                    System.out.println("Last line is " + i);
                    return null;
                }

                fileReader.nextLine();

            }
            String returnValue = fileReader.nextLine();
            fileReader.close();

            return (returnValue);

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
            return null;
        }

    }

    //print an entire paragraph. Useful for displaying art stored in .txt documents
    void printParagraph(int paragraphNum) {

        try{
            Scanner fileReader = new Scanner(this.file);
            int rowNum = getParagraphRow(paragraphNum);

            skipLines(fileReader, rowNum);

            while (true) {

                String currentLine = fileReader.nextLine();

                if (currentLine.length() != 0) {

                    System.out.println(currentLine);

                } else {
                    break;
                }

            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
        }

    }

    //gets the starting row of a specific paragraph
    int getParagraphRow(int paragraphNum) { // Paragraf 0 är första paragrafen. Returnerar vilken linje en paragraph (x) börjar på

        try{
            Scanner fileReader = new Scanner(this.file);
            int rowNum = 0;

            //starts the inner loop (paragraphNum) times, that loop only exits on new paragraphs.
            for (int i = 0; i < paragraphNum; i++) {

                while (true) {
                    rowNum ++;

                    if (!fileReader.hasNextLine() || (fileReader.nextLine().length() == 0))  {
                        break;
                    }

                }

            }
            fileReader.close();

            return rowNum;

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
        }

        return -1;

    }

    //skips all lines in a document up until the desired line.
    void skipLines(Scanner scanner, int lines) {

        for (int i = 0; i < lines; i++) {
            scanner.nextLine();
        }

    }

}
