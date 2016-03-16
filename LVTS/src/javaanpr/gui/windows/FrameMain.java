package javaanpr.gui.windows;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import javax.sound.midi.SysexMessage;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javaanpr.configurator.Configurator;
import javaanpr.gui.ReportGenerator;
import javaanpr.gui.tools.FileListModel;
import javaanpr.gui.tools.ImageFileFilter;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.imageanalysis.Char;
import javaanpr.imageanalysis.Photo;
import javaanpr.intelligence.Intelligence;
import javaanpr.recognizer.NeuralPatternClassificator;

public class FrameMain extends javax.swing.JFrame {
    public static ReportGenerator rg = new ReportGenerator();
    public static Intelligence systemLogic;
    public static String helpText = "" +
            "-----------------------------------------------------------\n"+
            "Automatic number plate recognition system\n"+
            "Copyright (c) Ondrej Martinsky, 2006-2007\n"+
            "\n"+
            "Licensed under the Educational Community License,\n"+
            "\n"+
            "Usage : java -jar anpr.jar [-options]\n"+
            "\n"+
            "Where options include:\n"+
            "\n"+
            "    -help         Displays this help\n"+
            "    -gui          Run GUI viewer (default choice)\n"+
            "    -recognize -i <snapshot>\n" +
            "                  Recognize single snapshot\n" +
            "    -recognize -i <snapshot> -o <dstdir>\n"+
            "                  Recognize single snapshot and\n"+
            "                  save report html into specified\n"+
            "                  directory\n"+
            "    -newconfig -o <file>\n"+
            "                  Generate default configuration file\n"+
            "    -newnetwork -o <file>\n"+
            "                  Train neural network according to\n"+
            "                  specified feature extraction method and\n"+
            "                  learning parameters (in config. file)\n"+
            "                  and saves it into output file\n"+
            "    -newalphabet -i <srcdir> -o <dstdir>\n"+
            "                  Normalize all images in <srcdir> and save\n"+
            "                  it to <dstdir>.";
    
    static final long serialVersionUID = 0;
        
    public class RecognizeThread extends Thread {
        FrameMain parentFrame = null;
        
        public RecognizeThread(FrameMain parentFrame) {
            this.parentFrame = parentFrame;
        }
        public void run() {
            String recognizedText = "";
            this.parentFrame.recognitionLabel.setText("processing ...");
            int index = this.parentFrame.selectedIndex;
            try {
                recognizedText = FrameMain.systemLogic.recognize(this.parentFrame.car);
            } catch (Exception ex) {
                this.parentFrame.recognitionLabel.setText("");
                return;
            }
            this.parentFrame.recognitionLabel.setText(recognizedText);
            this.parentFrame.fileListModel.fileList.elementAt(index).recognizedPlate = recognizedText;
        }
    }
    public class LoadImageThread extends Thread {
        FrameMain parentFrame = null;
        String url = null;
        public LoadImageThread(FrameMain parentFrame, String url) {
            this.parentFrame = parentFrame;
            this.url = url;
        }
        public void run() {
            try {
                this.parentFrame.car = new CarSnapshot(url);
                this.parentFrame.panelCarContent = this.parentFrame.car.duplicate().getBi();
                this.parentFrame.panelCarContent = Photo.linearResizeBi(this.parentFrame.panelCarContent,
                        this.parentFrame.panelCar.getWidth(),
                        this.parentFrame.panelCar.getHeight());
                this.parentFrame.panelCar.paint(this.parentFrame.panelCar.getGraphics());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    CarSnapshot car;
    BufferedImage panelCarContent;
    
    JFileChooser fileChooser;
    private FileListModel fileListModel;
    int selectedIndex = -1;
    
    /** Creates new form MainFrame */
    public FrameMain() {
        initComponents();
        
        // init : file chooser
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.fileChooser.setFileFilter(new ImageFileFilter());
        
        // init : window dimensions and visibility
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = this.getWidth();
        int height = this.getHeight();
        this.setLocation((screenSize.width - width)/2,(screenSize.height - height)/2);
        this.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recognitionLabel = new javax.swing.JLabel();
        panelCar = new JPanel() {
            static final long serialVersionUID = 0;
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(panelCarContent,0,0,null);
            }
        };
        fileListScrollPane = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList();
        recognizeButton = new javax.swing.JButton();
        bottomLine = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        imageMenu = new javax.swing.JMenu();
        openDirectoryItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaANPR");
        setResizable(false);

        recognitionLabel.setBackground(new java.awt.Color(0, 0, 0));
        recognitionLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        recognitionLabel.setForeground(new java.awt.Color(255, 204, 51));
        recognitionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recognitionLabel.setText("null");
        recognitionLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        recognitionLabel.setOpaque(true);

        panelCar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.jdesktop.layout.GroupLayout panelCarLayout = new org.jdesktop.layout.GroupLayout(panelCar);
        panelCar.setLayout(panelCarLayout);
        panelCarLayout.setHorizontalGroup(
            panelCarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 585, Short.MAX_VALUE)
        );
        panelCarLayout.setVerticalGroup(
            panelCarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 477, Short.MAX_VALUE)
        );

        fileListScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fileListScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fileList.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        fileList.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        fileList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fileListValueChanged(evt);
            }
        });
        fileListScrollPane.setViewportView(fileList);

        recognizeButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        recognizeButton.setText("recognize plate");
        recognizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recognizeButtonActionPerformed(evt);
            }
        });

        bottomLine.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        bottomLine.setText("Copyright (c) 2006 Ondrej Martinsky");

        menuBar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        imageMenu.setText("Image");
        imageMenu.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        openDirectoryItem.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        openDirectoryItem.setText("Load snapshots from directory");
        openDirectoryItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDirectoryItemActionPerformed(evt);
            }
        });
        imageMenu.add(openDirectoryItem);

        exitItem.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        imageMenu.add(exitItem);

        menuBar.add(imageMenu);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, bottomLine, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelCar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(fileListScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, recognitionLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .add(recognizeButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(fileListScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(recognizeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(recognitionLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(panelCar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bottomLine))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public static void newAlphabet(String srcdir, String dstdir) throws Exception { // NOT USED
        File folder = new File(srcdir);
        if (!folder.exists()) throw new IOException("Source folder doesn't exists");
        if (!new File(dstdir).exists()) throw new IOException("Destination folder doesn't exists");
        int x = Intelligence.configurator.getIntProperty("char_normalizeddimensions_x");
        int y = Intelligence.configurator.getIntProperty("char_normalizeddimensions_y");
        System.out.println("\nCreating new alphabet ("+x+" x "+y+" px)... \n");
        for (String fileName : folder.list()) {
            Char c = new Char(srcdir+File.separator+fileName);
            c.normalize();
            c.saveImage(dstdir+File.separator+fileName);
            System.out.println(fileName+" done");
        }
    }
    
    // DONE z danej abecedy precita deskriptory, tie sa nauci, a ulozi neuronovu siet
    public static void learnAlphabet(String destinationFile) throws Exception {
        try {
            File f = new File(destinationFile);
            f.createNewFile();
        } catch (Exception e) {
            throw new IOException("Can't find the path specified");
        }
        System.out.println();
        NeuralPatternClassificator npc = new NeuralPatternClassificator(true);
        npc.network.saveToXml(destinationFile);
    }    
    private void recognizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recognizeButtonActionPerformed
        String plate = null;
        
        // namiesto tohto urobime thread plate = Main.systemLogic.recognize(this.car);
        // thread code start
        new RecognizeThread(this).start();
        // thread code end
        
//            this.fileListModel.fileList.elementAt(this.selectedIndex).recognizedPlate = plate;
//            this.label.setText(plate);
        
    }//GEN-LAST:event_recognizeButtonActionPerformed
    
    private void fileListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fileListValueChanged
        int selectedNow = this.fileList.getSelectedIndex();
        
        if (selectedNow != -1 && this.selectedIndex != selectedNow) {
            this.recognitionLabel.setText(this.fileListModel.fileList.elementAt(selectedNow).recognizedPlate);
            this.selectedIndex = selectedNow;
            // proceed selectedNow
            String path = ((FileListModel.FileListModelEntry)this.fileListModel.getElementAt(selectedNow)).fullPath;
            //this.showImage(path);
            new LoadImageThread(this,path).start();
        }
    }//GEN-LAST:event_fileListValueChanged
    
    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitItemActionPerformed
        
    private void openDirectoryItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDirectoryItemActionPerformed
        int returnValue;
        String fileURL;
        
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.fileChooser.setDialogTitle("Load snapshots from directory");
        returnValue = this.fileChooser.showOpenDialog((Component)evt.getSource());
        
        if (returnValue != this.fileChooser.APPROVE_OPTION) return;
        
        fileURL = this.fileChooser.getSelectedFile().getAbsolutePath();
        File selectedFile = new File(fileURL);
        
        this.fileListModel = new FileListModel();
        for (String fileName : selectedFile.list()) {
            if (!ImageFileFilter.accept(fileName)) continue; // not a image
            this.fileListModel.addFileListModelEntry(fileName, selectedFile+File.separator+fileName);
        }
        this.fileList.setModel(fileListModel);
        
    }//GEN-LAST:event_openDirectoryItemActionPerformed
       public static void main(String args[]) throws Exception {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
if (args.length==0 || (args.length==1 && args[0].equals("-gui"))) {
            // DONE run gui
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
           // FrameComponentInit frameComponentInit = new FrameComponentInit(); // show wait
         FrameMain.systemLogic = new Intelligence(false);
           // frameComponentInit.dispose(); // hide wait
         // FrameMain mainFrame;
        // mainFrame = new FrameMain();

          // FrameMain.setDefaultLookAndFeelDecorated(true);
        } else if (args.length==3 &&
                args[0].equals("-recognize") &&
                args[1].equals("-i")
                ) {
            // DONE load snapshot args[2] and recognize it
            try {
                FrameMain.systemLogic = new Intelligence(false);
                System.out.println(systemLogic.recognize(new CarSnapshot(args[2])));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length==5 &&
                args[0].equals("-recognize") &&
                args[1].equals("-i") &&
                args[3].equals("-o")
                ) {
            // load snapshot arg[2] and generate report into arg[4]
            try {
                FrameMain.rg = new ReportGenerator(args[4]);     //prepare report generator
                FrameMain.systemLogic = new Intelligence(true); //prepare intelligence
               FrameMain.systemLogic.recognize(new CarSnapshot(args[2]));
                FrameMain.rg.finish();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
        } else if (args.length==3 &&
                args[0].equals("-newconfig") &&
                args[1].equals("-o")
                ) {
            // DONE save default config into args[2]
            Configurator configurator = new Configurator();
            try {
                configurator.saveConfiguration(args[2]);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length==3 &&
                args[0].equals("-newnetwork") &&
                args[1].equals("-o")
                ) {
            // DONE learn new neural network and save it into into args[2]
            try {
                learnAlphabet(args[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length==5 &&
                args[0].equals("-newalphabet") &&
                args[1].equals("-i") &&
                args[3].equals("-o")
                ) {
            // DONE transform alphabets from args[2] -> args[4]
            try {
                newAlphabet(args[2],args[4]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            // DONE display help
            System.out.println(helpText);
        }
        /* Create and display the form */
       java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
             new FrameMain().setVisible(true);
            }
        });}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bottomLine;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JList fileList;
    private javax.swing.JScrollPane fileListScrollPane;
    private javax.swing.JMenu imageMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openDirectoryItem;
    private javax.swing.JPanel panelCar;
    private javax.swing.JLabel recognitionLabel;
    private javax.swing.JButton recognizeButton;
    // End of variables declaration//GEN-END:variables
    
}
