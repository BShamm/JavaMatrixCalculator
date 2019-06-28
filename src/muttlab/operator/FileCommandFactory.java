/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import muttlab.operator.basic.UNKNOWNoperator;
import muttlab.operator.file.Unknownfile;

/**
 *
 * @author bens
 */
public class FileCommandFactory {
    // a constant map that holds all valid command words
    private final Map<String, Class<? extends FileCommand>> validFileCommands = new HashMap<>();
    
    private static final String PATH_PREFIX = "muttlab.operator";
    private final String MYPACKAGE; // name of the package with the operator-specific classes 
    private static final String NAMEFIELD = "NAME";
    private static final String OPERATORCLASS = "file.java";
    
    public FileCommandFactory(String version) {
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
            
            File src = new File(dist.getParentFile().getPath()+"/src/muttlab/operator/file/");
            
            
            //Look for classes whose name ends with "file.class"
            for (File f : src.listFiles((File file) -> {
                if (file.isDirectory()) {
                    return false;
                }
                String path = file.getAbsolutePath();
                return (path.endsWith(OPERATORCLASS));
            })) {
                String fullOperatorClassName = MYPACKAGE+f.getName(); // e.g. muttlab.operator.basic.ADDoperator.class
            
                try {
                    Class<? extends FileCommand> klass
                            = (Class<? extends FileCommand>) Class.forName(fullOperatorClassName.replaceFirst(".java", ""));
                    Field nameField = klass.getField(NAMEFIELD);
                    String opName = (String) nameField.get(null); // e.g. "+"
                    validFileCommands.put(opName,klass);
                
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
    
    public FileCommand newCommand(String word1) {
        Class<? extends FileCommand> cmd = validFileCommands.get(word1);
        if (cmd == null) cmd = Unknownfile.class;
        try {
            return cmd.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException|IllegalArgumentException|InvocationTargetException|InstantiationException ex) {
            Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "no constructor for "+"word1", ex);
        } catch (SecurityException|IllegalAccessException ex) {
            Logger.getLogger(CommandFactory.class.getName()).log(Level.SEVERE, "security exception", ex);
        }
        return null;
    }
    
    public Set<String> getFileCommands() {
        return validFileCommands.keySet();
    }
    
    
}
