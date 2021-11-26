package csv_vcard;
import ezvcard.VCard;
import ezvcard.parameter.RelatedType;
import ezvcard.property.Email;
import ezvcard.property.Gender;
import ezvcard.property.Organization;
import ezvcard.property.Related;
import ezvcard.property.StructuredName;
import ezvcard.property.Title;
public class CreateNewVcard {

	VCard vcard = new VCard();
		
	public CreateNewVcard(String line) {
		this(line.split(","));
	}

	public CreateNewVcard(String[] split) {
		this(split[1], split[2], split[3], split[4], split[5], split[6], split[7], split[8], split[9]);
	}

	public CreateNewVcard(String Title, String firstName, String lastName, String eMail, String Gender,
			String dept, String company, String Mother, String Father) {
		Title title = new Title(Title);
			
		StructuredName name = new StructuredName();
		name.setGiven(firstName);
		name.setFamily(lastName);
		
		Email email = new Email(eMail);
		
		Gender gender = new Gender(Gender.toUpperCase());
		
		Related mother = new Related();
		mother.setText(Mother);
		mother.getTypes().add(RelatedType.PARENT);
		
		Related father = new Related();
		father.setText(Father);
		father.getTypes().add(RelatedType.PARENT);
		
		Organization org = new Organization();
		org.getValues().add(company);
		org.getValues().add(dept);
	
		vcard.addTitle(title);
		vcard.setStructuredName(name);
		vcard.addEmail(email);
		vcard.setGender(gender);
		vcard.addRelated(mother);
		vcard.addRelated(father);
		vcard.addOrganization(org);
		
	}
}
