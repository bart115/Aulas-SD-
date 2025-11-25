import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private ArrayList<String> emails;

    public Contact(String name, int age, long phoneNumber, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String name() { return name; }
    public int age() { return age; }
    public long phoneNumber() { return phoneNumber; }
    public String company() { return company; }
    public List<String> emails() { return new ArrayList(emails); }

    // @TODO
    public void serialize(DataOutputStream out) throws IOException {
        //nome
        out.writeUTF(this.name);
        // age
        out.writeInt(this.age);
        //phone number
        out.writeLong(this.phoneNumber);
        // company ( flag + serialize item if flag is true )
        out.writeBoolean(this.company != null); // colocar um booleano a dizer se existe ou nao company
        if (this.company != null){
            out.writeUTF(this.company);
        }
        //emails (size + serialize each item)
        out.writeInt(this.emails.size());
        for (String s : this.emails ){
            out.writeUTF(s);

        }
    }

    // @TODO
    public static Contact deserialize(DataInputStream in) throws IOException {
        String name = in.readUTF();
        int age = in.readInt();
        long phoneNumber = in.readLong();

        //company
        String company = null;
        if (in.readBoolean()){
            company = in.readUTF();
        }
        //email
        ArrayList<String> email = new ArrayList<String>();
        int emailSize = in.readInt();
        for ( int i = 0 ; i < emailSize; i++){
            email.add(in.readUTF());
        }
        return new Contact(name,age,phoneNumber,company,email);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";");
        builder.append(this.age).append(";");
        builder.append(this.phoneNumber).append(";");
        builder.append(this.company).append(";");
        builder.append(this.emails.toString());
        builder.append("}");
        return builder.toString();
    }


}
