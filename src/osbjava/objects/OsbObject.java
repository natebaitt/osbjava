package osbjava.objects;

import osbjava.commands.*;
import osbjava.exceptions.*;
import osbjava.types.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Base class for storyboard objects.
 * <p>
 * Contains the declaration parameters of a storyboard object, and an {@link ArrayList}
 * of {@link OsbCommand}s that will be performed on the object.
 * <p>
 * Basic methods are provided to add and remove commands, as well as access the command list.
 * <p>
 *
 */
@SuppressWarnings("unused")
public abstract class OsbObject implements Serializable {


    // ============================================================

    // [VARIABLES]


    private final OsbObjectType    objectType;
    private OsbLayer               layer;
    private OsbOrigin              origin;
    private String                 filepath;
    private double                 x;
    private double                 y;
    private final List<OsbCommand> commandList;


    // ============================================================

    // [CONSTRUCTORS]


    public OsbObject(OsbObjectType objectType, OsbLayer layer, OsbOrigin origin, String filepath,
                     double x, double y) {
        this.objectType = objectType;
        this.layer = layer;
        this.origin = origin;
        this.filepath = filepath;
        this.x = x;
        this.y = y;
        this.commandList = new ArrayList<>();
    }


    // ============================================================

    // [GETTERS]


    public OsbObjectType getObjectType() {
        return this.objectType;
    }


    public OsbLayer getLayer() {
        return this.layer;
    }


    public OsbOrigin getOrigin() {
        return this.origin;
    }


    public String getFilepath() {
        return this.filepath;
    }


    public double getX() {
        return this.x;
    }


    public double getY() {
        return this.y;
    }


    public int getCommandCount() {
        return this.commandList.size();
    }


    // ============================================================

    // [SETTERS]


    public void setLayer(OsbLayer layer) {
        this.layer = layer;
    }


    public void setOrigin(OsbOrigin origin) {
        this.origin = origin;
    }


    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


    public void setX(double x) {
        this.x = x;
    }


    public void setY(double y) {
        this.y = y;
    }


    // ============================================================

    // [METHODS]


    /**
     * Adds a new {@link OsbCommand} to the end of the command list.
     *
     * @param command           Command object to add
     */
    public void addCommand(OsbCommand command) {
        this.commandList.add(command);
    }


    /**
     * Gets the OsbCommand at a specified index.
     *
     * @param index
     *
     * @return
     */
    public OsbCommand getCommand(int index) {
        if (index < 0 || index >= this.getCommandCount()) {
            throw new OutOfBoundsException();
        }
        return this.commandList.get(index);
    }


    public void removeCommand(int index) {
        if (index < 0 || index >= this.getCommandCount()) {
            throw new OutOfBoundsException();
        }
        this.commandList.remove(index);
    }


    /*
    public void optimizeCommands() {
        Automatically detect "unnecessary commands" to reduce file size.
    }
    /*


    /**
     * Returns the declaration syntax of an OsbObject as a string.
     *
     * @return String representation of an object declaration
     */
    public String toString() {

        String declarationStr = "";

        /* Concatenate object parameters */
        declarationStr += this.objectType.toString() + ","
                          + this.layer.toString() + ","
                          + this.origin.toString() + ","
                          + "\"" + this.filepath + "\"" + ","
                          + Math.round(this.x) + ","
                          + Math.round(this.y);

        /* If needed, concatenate animation parameters */
        if (this.objectType == OsbObjectType.ANIMATION) {
            declarationStr += "," + ((Animation) this).getFrameCount() + ","
                              + ((Animation) this).getFrameDelay() + ","
                              + ((Animation) this).getLoopType().toString();
        }

        /* If needed, concatenate dynamic animation parameters */
        if (this.objectType == OsbObjectType.DYNAMIC_ANIMATION) {
            declarationStr += "," + ((DynamicAnimation) this).getFrameCount() + ","
                    + ((DynamicAnimation) this).getFrameDelay() + ","
                    + ((DynamicAnimation) this).getLoopType().toString();
        }

        return declarationStr;
    }


    public String print() {

        String printStr = "";

        /* Concatenate declaration and command strings */
        printStr += this + "\n";
        for (int i = 0; i < this.getCommandCount(); i++) {
            printStr += this.getCommand(i).toString();
            if (i < this.getCommandCount() - 1) {
                printStr += "\n";
            }
        }

        return printStr;
    }


}
