import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;


/*
 *   Source: https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 */
public class FileChooserDemo extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JLabel baseDpLabel = new JLabel("Base DP: 360   ");
    JLabel resultDpLabel = new JLabel("Result DP");
    JTextField resultDpTextField = new JTextField("360");

    JTextArea log;
    JFileChooser fc;
    
    File file;
    private int folderCount = 0;
    private String srcDirPath1DirName = "";

    private String srcDirPath1 = "";
    private String srcDirPath2 = "";
    private String mDstPath = "";
    public FileChooserDemo() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(10,100);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();
//        fc.setCurrentDirectory(new java.io.File("."));
//        fc.setAcceptAllFileFilterUsed(false);
//        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Init a file
        file = null;
        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open file...");
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Translate DP...");
        saveButton.addActionListener(this);

     
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(baseDpLabel);
        buttonPanel.add(resultDpLabel);
        buttonPanel.add(resultDpTextField);


        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooserDemo.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                log.setText(fc.getCurrentDirectory().getName());
                
                
                final String srcDirPath = fc.getCurrentDirectory().getAbsolutePath();
                final String srcDirName = fc.getCurrentDirectory().getName();
                final String dstPath = fc.getCurrentDirectory().getParentFile().getAbsolutePath();
                System.out.println("srcDirPath is: " + srcDirPath);


                
                folderCount = folderCount%2;
                //清空顯示字串
                if (folderCount == 0){
                	log.setText("");
                	srcDirPath1 = "";
                	srcDirPath2 = "";
                }
                folderCount++;

                if (folderCount == 1 ){
                	log.setText("原始資料夾是: \n" + srcDirPath+"\n");
                	srcDirPath1 = srcDirPath;
                	srcDirPath1DirName = srcDirName;
                	mDstPath = dstPath;
                }else if (folderCount == 2) {
                    System.out.println("log.getText() is: " + log.getText());
                	log.setText("原始資料夾是: \n" + srcDirPath1+"\n" + "篩選過的資料夾是: \n" + srcDirPath+"\n");

//                	log.setText("篩選過的資料夾是: " + srcDirPath+"\n");
                	srcDirPath2 = srcDirPath;
                }
      
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        //Handle save button action.
        } else if (e.getSource() == saveButton) {
        	if(file!=null){
                try{
                	String dstDirName = resultDpTextField.getText();//尚未使用到
                	
                    final String dstDirPath = fc.getCurrentDirectory().getParentFile().getAbsolutePath();//fc.getCurrentDirectory().getParentFile() + "\11";

                	 if (folderCount == 2 ){
                         CopyFilesToDst copy = new CopyFilesToDst(srcDirPath1,srcDirPath1DirName,srcDirPath2,mDstPath);
                         copy.startCopy();
                         folderCount = 0;
                     }else{
                     	log.setText("尚未選擇完兩個資料夾(原始資料夾 與 篩選過的資料夾)");

                     }
//            		log.setText("Result DP is: " + resultDp + " ,開始translate " + file.getName() + "...");
//                    XMLParser parser = new XMLParser(file,resultDp);
//                    parser.start();
                }catch(NumberFormatException exception){
                	log.setText("輸入錯誤,請重新輸入");
                }   
        	}else{
            	log.setText("請先選擇檔案");
        	}
        }
        
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileChooserDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FileChooserDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

   
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
    
}