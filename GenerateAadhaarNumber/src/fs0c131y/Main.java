package fs0c131y;
	
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private JLabel lblCopiedToClipboard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 42));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(12, 13, 408, 110);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(generateRandomAadhaarNumber());
		JButton btnGenerateAadhaar = new JButton("Generate Aadhaar");
		btnGenerateAadhaar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(generateRandomAadhaarNumber());
			}
		});
		btnGenerateAadhaar.setBounds(145, 165, 150, 40);
		frame.getContentPane().add(btnGenerateAadhaar);
		
		lblCopiedToClipboard = new JLabel("Copied to clipboard or use ctrl+v");
		lblCopiedToClipboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopiedToClipboard.setBounds(12, 136, 408, 16);
		frame.getContentPane().add(lblCopiedToClipboard);
	}
    private String generateRandomAadhaarNumber() {
        StringBuilder randomAadhaarNumber = new StringBuilder();

        // According to the UID numbering scheme white paper available here:
        // https://drive.google.com/file/d/1uD1D6QyIiOC26UZvY2e4Fko9J3xF4TSy/view,
        // the first digit is a version number. 0 could be used as an "escape" and 1 could be
        // reserved for entities.
        Random rand = new Random();
        int versionNumber = rand.nextInt(8) + 2;
        randomAadhaarNumber.append(Integer.toString(versionNumber));

        for (int i = 0; i < 10; i++) {
            rand = new Random();
            int value = rand.nextInt(10);
            randomAadhaarNumber.append(Integer.toString(value));
        }

        String checksum = Verhoeff .generateVerhoeff(randomAadhaarNumber.toString());
        randomAadhaarNumber.append(checksum);
        StringSelection selec = new StringSelection(randomAadhaarNumber.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selec, selec);
        
        return randomAadhaarNumber.toString();
}
}
