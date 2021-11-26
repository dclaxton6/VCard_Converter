
package csv_vcard;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.json.JCardReader;
import ezvcard.io.json.JCardWriter;
import ezvcard.io.text.VCardReader;
import ezvcard.io.text.VCardWriter;

public class CsvToVcard {

	public static void main(String[] args) throws IOException  {
		deleteFilesInDirectory("src/main/resources/vcards");
		deleteFilesInDirectory("src/main/resources/json");
		deleteFilesInDirectory("src/main/resources/csv");
		//CreateJCard();
		//CreateVCard();
		//CreateCSV();
		//CreateCSVJCard();
	}
	
	public static List<VCard> readCsv(){
	String source = "src/main/resources/MOCK_DATA-3.csv";
	List<VCard> result = new ArrayList<>();
	try(BufferedReader r = new BufferedReader(new FileReader(source))){
		String line;
		while((line = r.readLine()) != null) {

			result.add(new CreateNewVcard(line).vcard);
			
		}
		return result;
	} catch(IOException e) {
		return result;
		}
	}
	
	public static void CreateVCard() throws IOException{
		List<VCard> vcards = readCsv();
		
		for(VCard vcard : vcards) {
			String[] uuidArr = UUID.randomUUID().toString().split("-");
			String fileName = "vcard_" + uuidArr[0] + ".vcf";
			File file = new File("src/main/resources/vcards/" + fileName);
			VCardWriter writer = new VCardWriter(file, VCardVersion.V4_0);
			writer.write(vcard);
			writer.close();
		}
	}
	
	public static void CreateJCard() throws IOException{ 
		List<VCard> vcards = readCsv();
		
		for(VCard vcard : vcards) {
			String[] uuidArr = UUID.randomUUID().toString().split("-");
			String fileName = "vcard_" + uuidArr[0] + ".json";
			File file = new File("src/main/resources/json/" + fileName);
			JCardWriter writer = new JCardWriter(file);
			writer.write(vcard);
			writer.close();
		}
	}

	//TODO need to refactor using reduce also need to figure out how to split company that includes  " " and , inside
	public static void CreateCSV() throws IOException {
		File index = new File("src/main/resources/vcards");
		File[] files = index.listFiles();
 		BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/csv/csv.txt"));
 		Long count = (long) 1;
		for(File file : files) {
		
			try {
				VCard vcard;
				VCardReader reader = new VCardReader(file);
				while((vcard = reader.readNext()) != null) {
				
				writer.write(count + ",");
				writer.write(vcard.getTitles().get(0).getValue().toString() + ",");
				writer.write(vcard.getStructuredName().getGiven().toString() + ",");
				writer.write(vcard.getStructuredName().getFamily().toString() + ",");
				writer.write(vcard.getEmails().get(0).getValue().toString() + ",");
				writer.write(vcard.getGender().getGender().toString() + ",");
				writer.write(vcard.getOrganization().getValues().get(0).toString() + ",");
				writer.write(vcard.getOrganization().getValues().get(1).toString() + ",");
				writer.write(vcard.getRelations().get(0).getText().toString() + ",");
				writer.write(vcard.getRelations().get(1).getText().toString());
				writer.newLine();
				reader.close();
				count++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.close();
	}
	
	public static void CreateCSVJCard() throws IOException {
		File index = new File("src/main/resources/json/");
		File[] files = index.listFiles();
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/csv/csvJ.txt"));
		Long count = (long) 1;
		for(File file : files) {
			VCard vcard;
			JCardReader reader = new JCardReader(file);
			try {
				while((vcard = reader.readNext()) != null) {
					writer.write(count + ",");
					writer.write(vcard.getTitles().get(0).getValue().toString() + ",");
					writer.write(vcard.getStructuredName().getGiven().toString() + ",");
					writer.write(vcard.getStructuredName().getFamily().toString() + ",");
					writer.write(vcard.getEmails().get(0).getValue().toString() + ",");
					writer.write(vcard.getGender().getGender().toString() + ",");
					writer.write(vcard.getOrganization().getValues().get(0).toString() + ",");
					writer.write(vcard.getOrganization().getValues().get(1).toString() + ",");
					writer.write(vcard.getRelations().get(0).getText().toString() + ",");
					writer.write(vcard.getRelations().get(1).getText().toString());
					writer.newLine();
					reader.close();
					count++;
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		writer.close();
	}
	public static void deleteFilesInDirectory(String source) {
		File index = new File(source);
		File[] files = index.listFiles();
		for(File file : files) {
			file.delete();
		}	
	}
	
}
