package com.groupfive;

public class Main {

    //static Dataset dataset = new Dataset(1996, "eminsafa");



    public static void main(String[] args) {

        Dataset fd = new Dataset(1, "Multilabel Topic Classification Dataset", 10);

        fd.addLabel(1, "bayi");
        fd.addLabel(2, "numara");
        fd.addLabel(3, "hat");

        fd.addInstance(1, "35 tl kesinti yapmış turkcell'den üzerime kayıtlı numara olmamasına rağmen iki aydır ekstremden 35'er tl. kesilmiştir.");
        fd.addInstance(2, "tele kurye işık hızında turkcell'den aldığım cihaz tele kurye denen ilgisiz, telefonlara bakmayan bir kurye tarafından 10 gündür elime ulaşılmadı. telefonlarıma bakılmıyor ne ciddiyetsiz kurumsallaşmamış bir kurye firması turkcell'e bu katkılarından dolayı teşekkürler!");

        fd.getLabelByText("bayi");
        // OUTPUT:
        User user = new User(1, "Name", "TESTv1");
        Datetime datetime = new Datetime("TESTv1");
        for (Instance instance : fd.getInstances()){

            Assignment assignment = instance.addAssignment(datetime, user);
            assignment.addLabelById(1);
            assignment.addLabelById(2);
        }

    }


}
=======

public class main {
    public static void main(String[] args) {

    Dataset dataset = new Dataset(1, "1", 2);
    ReadJSON read =new ReadJSON();
    WriteJSON write =new WriteJSON();
    read.readJson(dataset);
    write.write(dataset);

    }

}



>>>>>>> origin/main:iteration1/src/main.java
