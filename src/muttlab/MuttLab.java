package muttlab;


import muttlab.operator.Parser;
import muttlab.operator.Command;
import muttlab.matrix.Matrix;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;
import muttlab.io.InputManager;
import muttlab.io.OutputManager;
import muttlab.operator.CommandFactory;
import muttlab.operator.FileCommand;
import muttlab.operator.FileCommandFactory;

/**
 *
 * @author bens
 */

public class MuttLab {

    private final Parser parser;

    private final Deque<Matrix> stack;

    public Deque<Matrix> getStack() {
        return stack;
    }
    
    private String version; // TODO could this be final?
    
    private Boolean gui = false;

    public Boolean getGui() {
        return gui;
    }
    
     /** direct all output messages though outputMgr */
    private final OutputManager outputMgr;
    /** direct all input messages though inputMgr */
    private final InputManager inputMgr;
    
   
    /**
     * Create the tool and initialise its parser.
     * @param version the version of the tool
     * @param gui
     * @param out manager for output
     * @param in manager for input
     */
    public MuttLab(String version, OutputManager out, InputManager in, Boolean gui) {
        this.version = version;
        outputMgr = out;
        inputMgr = in;
        parser = new Parser(version, in, out);
        stack = new ArrayDeque<>();
        this.gui = gui;
    }
    
    
    public Parser getParser() {
        if(this.gui){
            return this.parser;
        }
        return null;
    }
    
    
    /**
     * Main exec routine. Loops until the end of the editing session.
     */
    public void exec() {
        outputMgr.write(welcome());

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the editing session is over.
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        outputMgr.write(muttley("hee, hee, hee!"));
        outputMgr.write("Thank you for using MuttLab.  Good bye.");
    }

    /**
     * Opening message for the user.
     */
    private String welcome() {
        return String.join("\n",
               muttley("Welcome to MuttLab!"),
               "MuttLab is an amazing new, matrix calculator.",
               "Type 'help' if you need help.",
               toString());
    }

    /**
     * Muttley icon for the user.
     * @param msg an extra message to show
     */
    private String muttley(String msg) {
        return String.join("\n",
           "╱▔╲╱▔▔▔▔╲╱▔╲",
           "▏ ╱  ▂ ▂ ╲ ▕",
           "╲╳▏ ┏▅┐ ┏▅┐╳╱",
           "  ▏╭╰━╯╰━┻━━╮",
           "╱▔▏▕     ▕▔▔▔▏",
           "▏ ▏▕      ╲▂╱▏",
           "▏ ▏ ╲   ▕╲ ┃▕╱▏",
           "▏ ╲  ▔▔▔▔▔▔▔▔▔▏ " + msg,
           "▏ ▏╲▂▂▂▂▂▂▂▂▂╱",
           "▏ ▏        ▕",
           "▏ ▏(textart4u.blogspot.com)");
    }

    /**
     * Given a command, execute the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the editing session, false otherwise.
     */
    public boolean processCommand(Command command) {
        boolean wantToQuit = command.execute(this);
        outputMgr.write(this.toString());
        return wantToQuit;
    }
    
    public boolean processCommandGUI(Command command) {
        boolean wantToQuit = command.execute(this);
        return wantToQuit;
        
    }
    
    public Matrix processFileCommand(FileCommand fcommand, String toggle){
        Matrix vector = fcommand.execute(stack, toggle);
        return vector;
    }

    /**
     * @return a string representation of the state of the tool
     */
    @Override
    public String toString() {
        if (stack.isEmpty())
             return "stack: -";
        else {
           StringBuilder str = new StringBuilder();
           Iterator<Matrix> iter = stack.descendingIterator();
           while(iter.hasNext()) {
               str.append(iter.next().toString());
               str.append("\n");
           }
           str.append("^^^ stack ^^^^");
           return str.toString();
        }   
    }

    /** @return the size of the operand stack */
    public int size() { return stack.size(); }
    
    /**
     * Push an operand onto the stack
     * @param m the matrix to push
     */
    public void push(Matrix m) { stack.push(m); }
    
    /** @return the operand popped from the top of the stack */
    public Matrix pop() { return stack.pop(); }

    /** @return the top of the stack without removing it */
    public Matrix peek() { return stack.peek(); }

    /** @return true if the operand stack is empty */
    public boolean isEmpty() { return stack.isEmpty(); }
    
    /** @param msg an error message to display */
    public void show(String msg) { outputMgr.write(msg); }
    
    /** @param msg an error message to display */
    public void error(String msg) { outputMgr.write(msg); }
    
    /** @return the version of the tool */
    public String getVersion() { return version; }
    
    /** @return the output manager */
    public OutputManager getOutputManager() { return outputMgr; }
    
    /** @return the input manager */
    public InputManager getInputManager() { return inputMgr; }

    public Set<String> getCommandWords() {
        CommandFactory cmdFactory = parser.getCommandFactory();
        return cmdFactory.getCommands();
    }
    
    public Set<String> getFileCommands() {
        FileCommandFactory fcmdFactory = parser.getFileCommandFactory();
        return fcmdFactory.getFileCommands();
    }
 
    /**
     * Print out some help information. Here we print some useless, cryptic
     * message and a list of the command words.
     * @return 
     */
    public void printHelp() {
        String[] msgs = {
            "You are using MatBench.",
            "",
            "Your command words are:"};
        outputMgr.write(msgs);
        outputMgr.write(String.join(" ", getCommandWords()));
        //"   show [ + - * .* dup help script quit" };
    }
}
