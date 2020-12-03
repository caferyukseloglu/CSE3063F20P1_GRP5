
public class main {
    public static void main(String[] args) {

    Dataset dataset = new Dataset(1, "1", 2);
    ReadJSON read =new ReadJSON();
    WriteJSON write =new WriteJSON();
    read.readJson(dataset);
    write.write(dataset);

    }

}



