import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDate;
import java.time.ZoneId;

public claws WorkSpots{

	static Scanner input;
	static File responsibilities;
	static File workspots;

	static ArrayList<String> rList;
	static ArrayList<String> wsList;
	
	public static void main(String[] args){

		initialize();

		System.out.println("Welcome! Working on different responsibilities in different places may enhance your focus.");
		System.out.println("Here, you can pair your responsibilities with places to work on them.");

		while(true){
			int option = menu();

	    	switch(option){
	    		case 1:
	    			addResponsibilities();
	    			break;
	    		case 2:
	    			addWorkSpots();
	    			break;
	    		case 3:
	    			viewData();
	    			break;
	    		case 4:
	    			pair();
	    			break;
	    		case 5:
	    			quit();
	    			break;
	    	}
		}


	}

	private static void initialize(){
		input = new Scanner(System.in);
		responsibilities = new File("responsibilities.txt");
		workspots = new File("workspots.txt");

		rList = new ArrayList<>();
		wsList = new ArrayList<>();

		try {
			if (!responsibilities.createNewFile())
    			fillList(rList, responsibilities);

			if (!workspots.createNewFile())
				fillList(wsList, workspots);
		}
		catch (IOException e) {
    		System.out.println("An error occurred creating files.");
    		e.printStackTrace();
    	}
	}

	private static void fillList(ArrayList<String> list, File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.ready()){
			list.add(br.readLine());
		}
		br.close();
	}

	private static int menu(){

		int result;

		System.out.println("\nMenu:");
		System.out.println("1) Add responsibilities");
		System.out.println("2) Add work spots");
		System.out.println("3) View your responsibilities and work spots");
		System.out.println("4) Pair responsibilities with work spots");
		System.out.println("5) Quit");

		System.out.print("\nEnter an option number: ");
		while(true){
			try{
				result = input.nextInt();
				if (result<1 || result>5)
					throw new RuntimeException();
				break;
			}
			catch (RuntimeException e){
				System.out.println("Error: Enter a number from 1-4, or 5 to quit");
			}
		}

		input.nextLine();

		return result;

	}

	private static void addResponsibilities(){

		System.out.println("Enter your responsibilities one at a time. Enter \"END\" to finish: ");
		while(true){
			String r = input.nextLine();
			if (r.toUpperCase().equals("END"))
				break;
			rList.add(r);
		}
		writeToFile(rList.toArray(new String[0]), responsibilities);

	}

	private static void addWorkSpots(){

		System.out.println("\nEnter your potential work spots one at a time. Enter \"END\" to finish: ");
		while(true){
			String ws = input.nextLine();
			if (ws.toUpperCase().equals("END"))
				break;
			wsList.add(ws);
		}
		writeToFile(wsList.toArray(new String[0]), workspots);

	}

	private static void viewData(){

		System.out.println("#################################################");
		System.out.println("Responsibilities:");
		for (String r : rList)
			System.out.println(r);
		System.out.println("#################################################");
		System.out.println("Work Spots:");
		for (String ws : wsList)
			System.out.println(ws);
		System.out.println("#################################################");
	}

	private static void pair(){
		
		String[] rArray = rList.toArray(new String[0]);
		String[] wsArray = wsList.toArray(new String[0]);
		shuffleArray(rArray);
		shuffleArray(wsArray);

		for (int i=0; i<rArray.length; i++){
			System.out.println("Work on " + rArray[i] + " at " + wsArray[i%wsArray.length]);
		}

	}

	// Fisher Yates Shuffle
	private static void shuffleArray(String[] arr){
		LocalDate date = LocalDate.now(ZoneId.systemDefault());
		int day = date.getDayOfYear()/14;
		Random r = new Random(day);
		for (int i=arr.length-1; i>0; i--){
			int index = r.nextInt(i+1);
			String temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
	}

	private static void writeToFile(String[] input, File file){
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

	private static void quit(){
		input.close();
		System.exit(0);
	}
}