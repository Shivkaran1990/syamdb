package symdb;

import java.io.IOException;

import symdb.service.ProcessRecords;

public class StartSymDbProcess {

	public static void main(String[] args) throws IOException {
		
		if(args.length>0)
		{
			String filelcoation=args[0];
			ProcessRecords starttask=new ProcessRecords();
			starttask.startInserts(filelcoation);
		}else
		{
			System.err.println("******Please provide file Name********");
		}
		
	}

}
