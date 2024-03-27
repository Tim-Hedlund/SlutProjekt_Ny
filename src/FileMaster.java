import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileMaster {
    File file;

    FileMaster(File file) {
        this.file = file;
    }

    ArrayList<String> returnParagraphStringList (int paragraphNum) {

        String returnString = "";

        try{
            Scanner fileReader = new Scanner(this.file);
            int rowNum = getParagraphRow(paragraphNum);

            skipLines(fileReader, rowNum);

            while (true) {

                String currentLine = fileReader.nextLine();

                if (currentLine.length() != 0) {

                    returnString = returnString + "," + currentLine;

                } else {
                    break;
                }

            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
        }

        return returnString;

    }

    int getFileParagraphCount(){

        try{
            Scanner fileReader = new Scanner(this.file);

            int paragraphCount = 0;

            while(fileReader.hasNextLine()) {

                if (fileReader.nextLine().equals("")) {
                    paragraphCount += 1;
                }

            }
            fileReader.close();

            return (paragraphCount);

        } catch (FileNotFoundException e) {
            System.out.println("Error,");
            e.printStackTrace();
            return -1;
        }


    }

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

    int getParagraphRow(int paragraphNum) { // Paragraf 0 är första paragrafen. Returnerar vilken linje en paragraph (x) börjar på

        try{
            Scanner fileReader = new Scanner(this.file);
            int rowNum = 0;

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

    void skipLines(Scanner scanner, int lines) {

        for (int i = 0; i < lines; i++) {
            scanner.nextLine();
        }

    }

}
