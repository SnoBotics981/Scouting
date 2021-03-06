package scout981;

import java.io.Console;

import scout981.csv.CSVWriter;
/**
 * This class handles all command input
 * It runs on a separate thread to prevent
 * lockups while attempting to get the next line of console input.
 * NONE OF THESE METHODS SHOULD BE OVERRIDEN TO PREVENT UNEXPECTED ERRORS!!!
 * @author Adam Snover
 *
 */
public class ConsoleInput extends Thread {

	private static class Holder {
		static final ConsoleInput INSTANCE = new ConsoleInput();
	}

	private ConsoleInput() {}

	@Override
	public void run() {
		Console c = System.console();
		while (Main.isRunning()) {
			String[] command = c.readLine().split(" ");
			
			if (command[0].equalsIgnoreCase("stop")) {
				CSVWriter.saveDataInCSV(true);
				//Main.getInstance().stopApp();
			} else if(command[0].equals("filename")) {
				if(command.length > 1) {
					CSVWriter.setFileName(command[1]);
					Main.logInfo("Scouting file name changed to " + CSVWriter.getFileName() + ".csv");
				} else {
					Main.logError("Not enough arguments!\nUsage: filename <name>");
				}
			} else if(command[0].equalsIgnoreCase("save")) {
				CSVWriter.saveDataInCSV(false);
			}
			
			else if (command[0].equalsIgnoreCase("help")) {
				Main.logInfo("\nstop - Stops program\nfilename - Name of the file to save. If no name is set, it will be named latest_scout.csv Usage: filename <name>\nsave - Saves current scouting information. Data WILL be overwritten if a file with the same name exists.");
			} else {
				Main.logError("Unkown Command");
			}
			c.flush();
		}
	}

	public static ConsoleInput getInstance() {
		return Holder.INSTANCE;
	}
}
