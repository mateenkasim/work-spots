import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDate;
import java.time.ZoneId;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WorkSpots implements ActionListener{

	private static File responsibilities;
	private static File workspots;
	private static ArrayList<String> rList;
	private static ArrayList<String> wsList;
	
	private ButtonGroup bg1, bg2;
	private CardLayout c;
	private JFrame window;
	private JPanel cards, menu, addResp, addWS, data, pairing;
	private JLabel welcome, instruct, addRespLabel, addWSLabel;
	private JButton button1, button2, button3, button4, button5, back;
	private JRadioButton radioAddR, radioAddWS, radioDeleteR, radioDeleteWS;
	private JTextField textResp, textWS;
	private boolean addingResp = false;
	private boolean addingWS = false;
	private final int CENTER = SwingConstants.CENTER;

	public static void main(String[] args){
		initialize();
		new WorkSpots();
	}

	private static void initialize(){
		File dir = new File(System.getProperty("user.home") + File.separator + 
								"Documents" + File.separator + "WorkSpotsData");
		if (!dir.exists()){
			dir.mkdirs();
		}
		responsibilities = new File(dir.getPath() + File.separator + "responsibilities.txt");
		workspots = new File(dir.getPath() + File.separator + "workspots.txt");

		rList = new ArrayList<>();
		wsList = new ArrayList<>();

		try {
			if (responsibilities.exists())
    			fillList(rList, responsibilities);

			if (workspots.exists())
				fillList(wsList, workspots);
		}
		catch (IOException e) {
    		System.out.println("An error occurred creating files.");
    		e.printStackTrace();
    	}
	}

	public WorkSpots() {
		bg1 = new ButtonGroup();
		bg2 = new ButtonGroup();
		c = new CardLayout(0,1);
		window = new JFrame();
		cards = new JPanel(c);
		menu = new JPanel();
		addResp = new JPanel();
		addWS = new JPanel();
		data = new JPanel();
		pairing = new JPanel();

		welcome = new JLabel("Welcome! Working on different responsibilities in different places may enhance your focus.", CENTER);
		instruct = new JLabel("Here, you can pair your responsibilities with places to work on them.", CENTER);
		addRespLabel = new JLabel("Enter your responsibilities one at a time, or type \"REMOVE ALL\". Hit enter after each: ", CENTER);
		addWSLabel = new JLabel("Enter your work spots one at a time, or type \"REMOVE ALL\". Hit enter after each to add: ", CENTER);

		button1 = new JButton("Add/delete responsibilities");
		button1.addActionListener(this);
		button2 = new JButton("Add/delete work spots");
		button2.addActionListener(this);
		button3 = new JButton("View your responsibilities and works spots");
		button3.addActionListener(this);
		button4 = new JButton("Pair responsibilities with work spots");
		button4.addActionListener(this);
		button5 = new JButton("Quit");
		button5.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);

		textResp = new JTextField("Enter responsibility here");
		textResp.setForeground(Color.GRAY);
		textResp.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		    	if (textResp.getText().equals("Enter responsibility here")){
		        	textResp.setText("");
		        	textResp.setForeground(Color.BLACK);
		    	}
		    }
		    public void focusLost(FocusEvent e) {
		    	if (textResp.getText().equals("")){
		        	resetTextField(textResp);
		    	}
		    }
		});
		textResp.addActionListener(this);

		textWS = new JTextField("Enter work spot here");
		textWS.setForeground(Color.GRAY);
		textWS.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		    	if (textWS.getText().equals("Enter work spot here")){
		        	textWS.setText("");
		        	textWS.setForeground(Color.BLACK);
		    	}
		    }
		    public void focusLost(FocusEvent e) {
		    	if (textWS.getText().equals("")){
		        	resetTextField(textWS);
		    	}
		    }
		});
		textWS.addActionListener(this);

		radioAddR = new JRadioButton("Add responsibility", true);
		radioAddR.addActionListener(this);
		radioDeleteR = new JRadioButton("Delete responsibility", false);
		radioDeleteR.addActionListener(this);
		bg1.add(radioAddR);
		bg1.add(radioDeleteR);
		radioAddWS = new JRadioButton("Add work spot", true);
		radioAddWS.addActionListener(this);
		radioDeleteWS = new JRadioButton("Delete work spot", false);
		radioDeleteWS.addActionListener(this);
		bg2.add(radioAddWS);
		bg2.add(radioDeleteWS);

		menu.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		menu.setLayout(new GridLayout(0,1));
		menu.add(welcome);
		menu.add(instruct);
		menu.add(button1);
		menu.add(button2);
		menu.add(button3);
		menu.add(button4);
		menu.add(button5);

		addResp.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		addResp.setLayout(new GridLayout(0,1));
		addResp.add(addRespLabel);
		addResp.add(radioAddR);
		addResp.add(radioDeleteR);
		addResp.add(textResp);

		addWS.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		addWS.setLayout(new GridLayout(0,1));
		addWS.add(addWSLabel);
		addWS.add(radioAddWS);
		addWS.add(radioDeleteWS);
		addWS.add(textWS);

		data.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		data.setLayout(new BorderLayout(0,1));
		pairing.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		pairing.setLayout(new BorderLayout(0,1));

		cards.add(menu, "Menu");
		cards.add(addResp, "Add responsibilities");
		cards.add(addWS, "Add work spots");
		cards.add(data, "Data");
		cards.add(pairing, "Pairing");

		window.add(cards, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Work Spots");
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Object b = e.getSource();

		if (b == back){
			if (addingResp){
				writeToFile(rList.toArray(new String[0]), responsibilities);
				resetTextField(textResp);
				addingResp = false;
			}
			if (addingWS){
				writeToFile(wsList.toArray(new String[0]), workspots);
				resetTextField(textWS);
				addingWS = false;
			}
			c.show(cards, "Menu");
			window.pack();
		}
		else if (b == button1)
			addResponsibilities();
		else if (b == button2)
			addWorkSpots();
		else if (b == button3)
			viewData();
		else if (b == button4)
			pair();
		else if (b == button5)
			quit();
		else if (b == textResp){
			if (!textResp.getText().equals("")){
				if (textResp.getText().toUpperCase().equals("REMOVE ALL"))
					rList.clear();
				else if (radioAddR.isSelected())
					rList.add(textResp.getText());
				else
					removeFromList(rList,textResp.getText());
			}
			textResp.setText("");
			addRespLabel.requestFocusInWindow();
		}
		else if (b == textWS){
			if (!textWS.getText().equals("")){
				if (textWS.getText().toUpperCase().equals("REMOVE ALL"))
					wsList.clear();
				else if (radioAddWS.isSelected())
					wsList.add(textWS.getText());
				else
					removeFromList(wsList,textWS.getText());
			}
			textWS.setText("");
			addWSLabel.requestFocusInWindow();
		}
	}

	private static void fillList(ArrayList<String> list, File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.ready()){
			list.add(br.readLine());
		}
		br.close();
	}

	private void addResponsibilities(){
		addingResp = true;
		addResp.add(back, BorderLayout.SOUTH);
		c.show(cards, "Add responsibilities");
		window.pack();
	}

	private void addWorkSpots(){
		addingWS = true;
		addWS.add(back, BorderLayout.SOUTH);
		c.show(cards, "Add work spots");
		window.pack();
	}

	private void viewData(){
		data.removeAll();
		data.updateUI();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

		JLabel dR = new JLabel("<html><div align=center>RESPONSIBILITIES:<br>");
		for (String r : rList)
			dR.setText(dR.getText()+r+"<br>");
		dR.setHorizontalAlignment(CENTER);
		p.add(dR);
		JLabel dWS = new JLabel("<html><div align=center>WORK SPOTS:<br>");
		for (String ws : wsList)
			dWS.setText(dWS.getText()+ws+"<br>");
		dWS.setHorizontalAlignment(CENTER);
		p.add(dWS);

		data.add(p, BorderLayout.CENTER);
		data.add(back, BorderLayout.SOUTH);
		c.show(cards, "Data");
		window.pack();
	}

	private void pair(){
		
		pairing.removeAll();
		pairing.updateUI();

		String[] rArray = rList.toArray(new String[0]);
		String[] wsArray = wsList.toArray(new String[0]);
		boolean newShuffle = shuffleArray(rArray);
		shuffleArray(wsArray);
		if (newShuffle){
			JLabel shuffled = new JLabel("Today is the first day of a new pairing!", CENTER);
			pairing.add(shuffled, BorderLayout.NORTH);
		}

		JLabel p = new JLabel("<html><div align=center>PAIRINGS<br><br>", CENTER);
		for (int i=0; i<rArray.length; i++){
			p.setText(p.getText()+"Work on " + rArray[i] + " at " + wsArray[i%wsArray.length] + "<br>");
		}
		pairing.add(p);
		pairing.add(back, BorderLayout.SOUTH);
		c.show(cards, "Pairing");
		window.pack();
	}

	private void quit(){
		System.exit(0);
	}

	// Fisher Yates Shuffle
	private boolean shuffleArray(String[] arr){
		LocalDate date = LocalDate.now(ZoneId.systemDefault());
		int day = date.getDayOfYear()/14;
		Random r = new Random(day);
		for (int i=arr.length-1; i>0; i--){
			int index = r.nextInt(i+1);
			String temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
		return day != (date.getDayOfYear()-1)/14;
	}

	private void writeToFile(String[] input, File file){
	    try {
	    	FileWriter w = new FileWriter(file);
	    	for (String r : input)
	    		w.write(r+System.getProperty("line.separator"));
	    	w.close();
	    } 
	    catch (IOException e) {
	    	System.out.println("An error occurred writing to " + file.getName() + ".");
	    	e.printStackTrace();
	    }
	}

	private boolean removeFromList(ArrayList list, String key){
		for (int i=0; i<list.size(); i++){
			if (list.get(i).equals(key)){
				list.remove(i);
				return true;
			}
		}
		return false;
	}
	private void resetTextField(JTextField jtf){
		String s = addingResp ? "Enter responsibility here" : "Enter work spot here";
		jtf.setText(s);
		jtf.setForeground(Color.GRAY);
	}

}