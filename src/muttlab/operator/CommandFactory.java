package muttlab.operator;

import muttlab.operator.basic.UNKNOWNoperator;
import muttlab.operator.basic.*;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author bens
 */

public class CommandFactory
{
    // a constant map that holds all valid command words
    private final Map<String, Class<? extends Command>> validCommands = new HashMap<>();
    
    private static final String PATH_PREFIX = "muttlab.operator";
    private final String MYPACKAGE; // name of the package with the operator-specific classes 
    private static final String NAMEFIELD = "NAME";
    private static final String OPERATORCLASS = "operator.java";
    
    public CommandFactory(String version) {
        MYPACKAGE = PATH_PREFIX + "." + version + ".";
        makeValidCommands(version);
    }

    /**
     * Create a map of valid command names
     * @param version the version of the tool, e.g. "basic"
     */
    private void makeValidCommands(String version) {
        // I'm the only class I know about, but issue with .jar files
        
        try{
            
            URI uri = CommandFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            
            File parent = new File(uri.getPath()).getParentFile();
            
            File dist = new File(parent.getParentFile().getPath());
            
            File src = new File(dist.getParentFile().getPath()+"/src/muttlab/operator/basic/");
            
            
            //Look for classes whose name ends with "operator.class"
            for (File f : src.listFiles((File file) -> {
                if (file.isDirectory()) {
                    return false;
                }
                String path = file.getAbsolutePath();
                return (path.endsWith(OPERATORCLASS));
            })) {
                String fullOperatorClassName = MYPACKAGE+f.getName(); // e.g. muttlab.operator.basic.ADDoperator.class
            
                try {
                    Class<? extends Command> klass
                            = (Class<? extends Command>) Class.forName(fullOperatorClassName.replaceFirst(".java", ""));
                    Field nameField = klass.getField(NAMEFIELD);
                    String opName = (String) nameField.get(null); // e.g. "+"
                    validCommands.put(opName,klass);
                
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "Got a bad operator class", ex);
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "Got a bad name for operators' names", ex);
                } catch (SecurityException|IllegalArgumentException|IllegalAccessException ex) {
                    Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    /**
     * Check whether a given String is a valid command word. 
     * @param aString the string
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString) { return validCommands.containsKey(aString); }
    
    public Command newCommand(String word1, String rest) {
        Class<? extends Command> cmd = validCommands.get(word1);
        if (cmd == null) cmd = UNKNOWNoperator.class;
        Class[] args = {String.class, String.class};
        try {
            return cmd.getDeclaredConstructor(args).newInstance(word1, rest);
        } catch (NoSuchMethodException|IllegalArgumentException|InvocationTargetException|InstantiationException ex) {
            Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "no constructor for "+"word1", ex);
        } catch (SecurityException|IllegalAccessException ex) {
            Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "security exception", ex);
        }
        return null;
    }

    public Set<String> getCommands() {
        return validCommands.keySet();
    }
}
