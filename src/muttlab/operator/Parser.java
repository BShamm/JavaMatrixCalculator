package muttlab.operator;

import java.io.FileInputStream;import java.io.InputStream;
import java.util.Scanner;
import muttlab.io.InputManager;
import muttlab.io.OutputManager;

/**
 * 
 * @author bens
 */
public class Parser 
{
    private final CommandFactory commands;  // holds all valid command words
    private final FileCommandFactory fileCommands;
    private Scanner reader;         // source of command input
    private InputManager inputMgr;
    private OutputManager outputMgr;

    /**
     * Create a parser to read from the terminal window.
     * @param version the version of the tool
     * @param in an input manager
     * @param out an output manager
     */
    public Parser(String version, InputManager in, OutputManager out) {
        this.inputMgr = in;
        this. outputMgr = out;
        commands = new CommandFactory(version);
        fileCommands = new FileCommandFactory("file");
        reader = new Scanner(in.getInputStream());
    }

    public void setInputStream(FileInputStream str) { 
        reader = new Scanner(str);
    }
    /**
     * @return The next command from the user.
     */
    public Command getCommand() {
        outputMgr.prompt();     // print prompt
        String inputLine = reader.nextLine(); // the full input line

        // Find command and argument on the line.
        String[] cmdAndArgs = inputLine.split(" ", 2);
        String word1 = cmdAndArgs.length>=1 ? cmdAndArgs[0] : null;
        String rest = cmdAndArgs.length>=2 ? cmdAndArgs[1] : null;
               
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        return commands.newCommand(word1, rest);
        
    }
    
    /**
     * @param command
     * @param rest
     * @return The next command from the user.
     */
    public Command getCommand(String command, String rest) {
        
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        return commands.newCommand(command, rest);
        
    }
    
    public FileCommand getFCommand(String fCommand){
        return fileCommands.newCommand(fCommand);
    }
    

    public CommandFactory getCommandFactory() {  return commands; }
    
    public FileCommandFactory getFileCommandFactory() { return fileCommands; }
}
