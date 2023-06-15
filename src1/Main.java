import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
class People{
	public int id;
	public String name;
	public String gender;
	public int weight;
	public int height;
	public int age;
	public int dailyNeed;
	public int currentCal;
	public int burnedCal;
	public int takenCal;

}
class Sport{
	public int id;
	public String name;
	public int kcal;
}

class Food{
	public int id;
	public String name;
	public int kcal;
}

class Command{
	public int notePeople;
	public int noteFood;
	public int noteSport;         
}



public class Main 
{
	public static double dailyCalculate(String gender,int weight,int height,int age){
		if(gender.equals("male")) {	
			return Math.round(66+ 13.75*weight + 5*height - 6.8*age);			
		}
		else{
			return Math.round(665+ 9.6*weight + 1.7*height - 4.7*age);
		}
	}	
		
	
	public static void main(String[] args) throws IOException 
	{
		
		try
		{
			File txt1 = new File("food.txt");
			Scanner foodTxt = new Scanner(txt1);
			File txt2 = new File("sport.txt");
			Scanner sportTxt = new Scanner(txt2);
			File txt3 = new File("people.txt");
			Scanner peopleTxt = new Scanner(txt3);
			File txt4 = new File(args[0]);
			Scanner commandTxt = new Scanner(txt4);
			File txt5 = new File("monitoring.txt");
			FileWriter fw = new FileWriter(txt5);
			PrintWriter pw = new PrintWriter(fw);
			
			
			
			Food[] foods = new Food[100];
			int temp = 0;
			while(foodTxt.hasNext()) {
				String data = foodTxt.nextLine();
				String datas[] = data.split("	");				
				foods[temp] = new Food();
				foods[temp].id = Integer.parseInt(datas[0]);
				foods[temp].name= datas[1];
				foods[temp].kcal= Integer.parseInt(datas[2]);
				temp++;								
			}
			
			Sport[] sports = new Sport[100];
			int temp2 = 0;
			while(sportTxt.hasNext()) {
				String data = sportTxt.nextLine();
				String datas[] = data.split("	");				
				sports[temp2] = new Sport();
				sports[temp2].id = Integer.parseInt(datas[0]);
				sports[temp2].name= datas[1];
				sports[temp2].kcal= Integer.parseInt(datas[2]);
				temp2++;	
			}
			
			People[] peoples = new People[100];
			int temp3 = 0;
			while(peopleTxt.hasNext()) {
				String data = peopleTxt.nextLine();
				String datas[] = data.split("	");				
				peoples[temp3] = new People();
				peoples[temp3].id = Integer.parseInt(datas[0]);
				peoples[temp3].name= datas[1];
				peoples[temp3].gender= datas[2];
				peoples[temp3].weight = Integer.parseInt(datas[3]);
				peoples[temp3].height = Integer.parseInt(datas[4]);
				peoples[temp3].age = 2022-Integer.parseInt(datas[5]);
				peoples[temp3].burnedCal =0;
				peoples[temp3].takenCal =0;
				peoples[temp3].currentCal=0;
				peoples[temp3].dailyNeed = (int)dailyCalculate(peoples[temp3].gender,peoples[temp3].weight,peoples[temp3].height,peoples[temp3].age);
				temp3++;	
			}

			
			Command notes = new Command();
			while(commandTxt.hasNext()) {
				String data = commandTxt.nextLine();
				String datas[] = data.split("	");
				if(datas.length==3) {                   				
					for(int i=0; i<peoples.length;i++) {        
						if(peoples[i]!= null && Integer.parseInt(datas[0])==peoples[i].id) {
							notes.notePeople = i;
							break;
						}				
					}
					if(Integer.parseInt(datas[1].substring(0,1))==1) {          //food
						for(int i=0; i<foods.length; i++) {
							if(foods[i] != null && Integer.parseInt(datas[1])==foods[i].id) {
								notes.noteFood =i;
								peoples[notes.notePeople].takenCal += foods[notes.noteFood].kcal*Integer.parseInt(datas[2]);
								pw.println(peoples[notes.notePeople].id+"\thas"+"\ttaken\t"+foods[notes.noteFood].kcal*Integer.parseInt(datas[2])+
										"kcal\tfrom\t"+foods[notes.noteFood].name);
								break;
							}
						}
					}
					if(Integer.parseInt(datas[1].substring(0,1))==2) {          //sport
						for(int i=0; i<sports.length; i++) {
							if(sports[i] != null && Integer.parseInt(datas[1])==sports[i].id) {
								notes.noteSport =i;
								peoples[notes.notePeople].burnedCal += sports[notes.noteSport].kcal*Integer.parseInt(datas[2])/60;
								pw.println(peoples[notes.notePeople].id+"\thas"+"\tburned\t"+sports[notes.noteSport].kcal*Integer.parseInt(datas[2])/60+
										"kcal\tthanks"+"\tto\t"+sports[notes.noteSport].name);

								break;
							}
						}
					}
					peoples[notes.notePeople].currentCal = peoples[notes.notePeople].takenCal - peoples[notes.notePeople].dailyNeed- peoples[notes.notePeople].burnedCal;
				}		
										
				
				else if(datas[0].equals("printList")){
					for(int i=0; i<peoples.length; i++) {
						if(peoples[i] != null && (peoples[i].burnedCal !=0 || peoples[i].takenCal !=0)) {
							pw.println(peoples[i].name+"\t"+peoples[i].age+"\t"+peoples[i].dailyNeed+"kcal\t"+peoples[i].takenCal+"kcal\t"+peoples[i].burnedCal
									+"kcal\t"+peoples[i].currentCal+"kcal");
						}				
					}					
				}				
				else if(datas[0].equals("printWarn")) {
					int persontemp=0;
					for(int i=0; i<peoples.length;i++) {
						if(peoples[i]!= null && peoples[i].currentCal>0) {
							pw.println(peoples[i].name+"\t"+peoples[i].age+"\t"+peoples[i].dailyNeed+"kcal\t"+peoples[i].takenCal+"kcal\t"+peoples[i].burnedCal
									+"kcal\t"+peoples[i].currentCal+"kcal");
							persontemp++;
						}			
					}
					if(persontemp==0) {
						pw.println("there\tis\tno\tsuch\tperson");
					}
				}
				else {                                                                         //print(xxxxx) komutu
					for(int i=0; i<peoples.length;i++) {         						
						if(peoples[i]!= null && Integer.parseInt(datas[0].substring(6,datas[0].length()-1))==peoples[i].id) {
							pw.println(peoples[i].name+"\t"+peoples[i].age+"\t"+peoples[i].dailyNeed+"kcal\t"+peoples[i].takenCal+"kcal\t"+peoples[i].burnedCal
									+"kcal\t"+peoples[i].currentCal+"kcal");
							break;
						}
					}
				}
				if(commandTxt.hasNext()) {
					pw.println("***************"); 
				}
			}
			

			
			
			
			
			
			
			
			
			
			
			pw.close();
			commandTxt.close();
			peopleTxt.close();
			sportTxt.close();
			foodTxt.close();
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("txt cannot be found.");
		}
				
	}

}
