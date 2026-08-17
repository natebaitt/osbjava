package osbjava.objects;

import osbjava.exceptions.*;
import osbjava.types.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("unused")
public class OsbFile implements Serializable {


    // ============================================================

    // [VARIABLES]


    private final List<OsbObject> backgroundObjects;
    private final List<OsbObject> failObjects;
    private final List<OsbObject> passObjects;
    private final List<OsbObject> foregroundObjects;


    // ============================================================

    // [CONSTRUCTORS]


    public OsbFile() {
        backgroundObjects = new ArrayList<>();
        failObjects =       new ArrayList<>();
        passObjects =       new ArrayList<>();
        foregroundObjects = new ArrayList<>();
        System.out.println(OsbUtils.CONSOLE_TAG + "New OsbFile created.");
    }


    public OsbFile(String filepath) {

        System.out.println(OsbUtils.CONSOLE_TAG + "Importing binary file from " + filepath + "...");
        File file = new File(filepath);
        ObjectInputStream binaryReader;

        /* Check if file is directory */
        if (file.isDirectory()) {
            throw new FileIsDirectoryException();
        }

        /* Check if file exists */
        if (!file.exists()) {
            throw new FileDoesNotExistException();
        }

        /* Read binary objects into layers */
        try {
            binaryReader = new ObjectInputStream(new FileInputStream(file));
            backgroundObjects = (List) binaryReader.readObject();
            failObjects       = (List) binaryReader.readObject();
            passObjects       = (List) binaryReader.readObject();
            foregroundObjects = (List) binaryReader.readObject();
            binaryReader.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileIOException();
        }

        System.out.println(OsbUtils.CONSOLE_TAG + "New OsbFile created from binary file.");

    }


    // ============================================================

    // [METHODS]


    public void addObject(OsbObject object) {
        switch (object.getLayer()) {
            case BACKGROUND: backgroundObjects.add(object); break;
            case FAIL:       failObjects.add(object); break;
            case PASS:       passObjects.add(object); break;
            case FOREGROUND: foregroundObjects.add(object); break;
        }
    }


    public void addObjects(List<OsbObject> objects) {
        for (OsbObject object : objects) {
            this.addObject(object);
        }
    }


    public void addObjects(OsbObject... objects) {
        for (OsbObject object : objects) {
            this.addObject(object);
        }
    }


    public OsbObject getObject(OsbLayer layer, int index) {

        switch (layer) {

            case BACKGROUND: {
                if (index >= backgroundObjects.size()) {
                    throw new OutOfBoundsException();
                }
                return backgroundObjects.get(index);
            }

            case FAIL: {
                if (index >= failObjects.size()) {
                    throw new OutOfBoundsException();
                }
                return failObjects.get(index);
            }

            case PASS: {
                if (index >= passObjects.size()) {
                    throw new OutOfBoundsException();
                }
                return passObjects.get(index);
            }

            case FOREGROUND: {
                if (index >= foregroundObjects.size()) {
                    throw new OutOfBoundsException();
                }
                return foregroundObjects.get(index);
            }

            default:
                return null;
        }

    }


    public List<OsbObject> getLayer(OsbLayer layer) {
        return switch (layer) {
            case BACKGROUND -> backgroundObjects;
            case FAIL -> failObjects;
            case PASS -> passObjects;
            case FOREGROUND -> foregroundObjects;
        };
    }


    public void combine(OsbFile... files) {
        for (OsbFile file : files) {
            this.addObjects(file.getLayer(OsbLayer.BACKGROUND));
            this.addObjects(file.getLayer(OsbLayer.FAIL));
            this.addObjects(file.getLayer(OsbLayer.PASS));
            this.addObjects(file.getLayer(OsbLayer.FOREGROUND));
        }
    }


    // ============================================================

    // [FILE HANDLING]


    private Object open(String filepath, boolean isBinary) {

        File file = new File(filepath);

        /* Check if file is directory */
        if (file.isDirectory()) {
            throw new FileIsDirectoryException();
        }

        /* Check if file exists */
        if (file.exists()) {

            Scanner scanner = new Scanner(System.in);
            String input;

            /* Prompt user for overwrite */
            System.out.println("A file already exists in:\n");
            System.out.println("\t" + filepath + "\n");

            /* Loop if inputs are invalid */
            while (true) {

                System.out.print("Would you like to remove the original file? (y/n): ");
                input = scanner.nextLine();
                input = input.replace(" ", "");

                /* If no, return null writer */
                if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                    System.out.println("File was not overwritten. Proceeding with program...");
                    return null;
                }

                /* If yes, delete original file */
                else if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    if (!file.delete()) {
                        System.out.println("File could not be successfully removed. Aborting program.");
                        System.exit(0);
                    }
                    System.out.println("File successfully removed. Proceeding with program...");
                    break;
                }

                /* Input was invalid */
                else {
                    System.out.println("Input was invalid!");
                }

            }
        }

        /* Open file based on type and handle potential exceptions */
        if (isBinary) {
            ObjectOutputStream binaryWriter;
            try {
                binaryWriter = new ObjectOutputStream(new FileOutputStream(file));
            } catch (IOException e) {
                throw new FileIOException();
            }
            return binaryWriter;
        } else {
            PrintWriter writer;
            try {
                writer = new PrintWriter(new FileOutputStream(file));
            } catch (IOException e) {
                throw new FileIOException();
            }
            return writer;
        }

    }


    public void export(String filepath) {

        /* Attempt to open file */
        System.out.println(OsbUtils.CONSOLE_TAG + "Exporting OsbFile to " + filepath + "...");
        PrintWriter writer = (PrintWriter) open(filepath, false);

        /* Write all objects from each layer */
        if (writer != null) {

            System.out.println("\tWriting the header...");
            writer.println("[Events]");
            writer.println("//Background and video events");

            System.out.println("\tWriting background objects...");
            writer.println("//Storyboard Layer 0 (Background)");
            for (OsbObject object : backgroundObjects) {
                this.writeObject(writer, object);
            }

            System.out.println("\tWriting fail objects...");
            writer.println("//Storyboard Layer 1 (Fail)");
            for (OsbObject object : failObjects) {
                this.writeObject(writer, object);
            }

            System.out.println("\tWriting pass objects...");
            writer.println("//Storyboard Layer 2 (Pass)");
            for (OsbObject object : passObjects) {
                this.writeObject(writer, object);
            }

            System.out.println("\tWriting foreground objects...");
            writer.println("//Storyboard Layer 3 (Foreground)");
            for (OsbObject object : foregroundObjects) {
                this.writeObject(writer, object);
            }

            writer.close();
            System.out.println(OsbUtils.CONSOLE_TAG + "Successfully exported storyboard file.");

        }

    }


    public void exportBinary(String filepath) {

        /* Attempt to open file */
        System.out.println(OsbUtils.CONSOLE_TAG + "Exporting binary data of OsbFile to " + filepath + "...");
        ObjectOutputStream binaryWriter = (ObjectOutputStream) open(filepath, true);

        /* Write layers as binary objects */
        if (binaryWriter != null) {
            try {
                binaryWriter.writeObject(backgroundObjects);
                binaryWriter.writeObject(failObjects);
                binaryWriter.writeObject(passObjects);
                binaryWriter.writeObject(foregroundObjects);
                binaryWriter.close();
            } catch (IOException e) {
                throw new FileIOException();
            }
            System.out.println(OsbUtils.CONSOLE_TAG + "Successfully exported binary file.");
        }

    }


    private void writeObject(PrintWriter writer, OsbObject object) {
        writer.println(object.toString());
        for (int i = 0; i < object.getCommandCount(); i++) {
            writer.println(object.getCommand(i).toString());
        }
    }


}
