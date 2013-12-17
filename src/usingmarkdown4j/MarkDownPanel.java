package usingmarkdown4j;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class MarkDownPanel extends JPanel 
			implements DocumentListener{



	/**
	 * 
	 */
	private static final long serialVersionUID = 381861186434771766L;

	/**
	 * Create the panel.
	 */
	JEditorPane leftPane;
	JTextPane rightPane;

	public MarkDownPanel() {
		super();
		setLayout(new BorderLayout());
		
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		
		
		leftPane = new JEditorPane();	
		JScrollPane editorPane = new JScrollPane(leftPane);
		splitPane.setLeftComponent(editorPane);
		leftPane.getDocument().addDocumentListener(this);
		
		
		rightPane = new JTextPane();
		rightPane.setEditable(false);
		rightPane.setContentType("text/html");
		//rightPane.setText("<p><img src=\"http://mouapp.com/Mou_128.png\" alt=\"Mou icon\" /></p>");
		JScrollPane textPane = new JScrollPane(rightPane);
		splitPane.setRightComponent(textPane);
		
		add(splitPane);
	}
	
	@Override
	public void changedUpdate(DocumentEvent de) {
		//do not need in plain text file
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		try {
			textUpdate(de);
		} catch (BadLocationException be) {
			be.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		try {
			textUpdate(de);
		} catch (BadLocationException be) {
			be.printStackTrace();
		}
	}
	
	private void textUpdate(DocumentEvent de) throws BadLocationException{
		Document doc = de.getDocument();
		String text = doc.getText(0, doc.getLength());
		String html = Processor.process(text);

		rightPane.setText(html);

	}
	
	protected void addStylesToDocument(StyledDocument doc){
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");
		
		Style s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);
	}
	

	
	
    private static void createAndShowGUI() {
        //Create and set up the window.
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	Dimension d = tk.getScreenSize();
        JFrame frame = new JFrame("My Mardown Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.getContentPane().add(new MarkDownPanel());
        frame.setSize((int)d.getWidth()/2, (int)(d.getHeight()*0.75));
        frame.setLocation((int)d.getWidth()/4, (int)d.getHeight()/4);
        //Display the window.
        frame.setVisible(true);
    }
	
	public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 //Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
            }
        });		
	}

}
