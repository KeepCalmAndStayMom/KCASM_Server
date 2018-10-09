package server.net_influence;

public class App {

    public static void main(String[] args) {

        NetSmile netSmile = new NetSmile("src\\main\\resources\\Prova2.xdsl");

        int[] testEvidence = new int[] {1, 0, 0, 3, 0, 0};

        netSmile.clearNet();
        netSmile.setAllEvidence(testEvidence);
        netSmile.runNet();

        System.out.println(netSmile.getResultUtility());

    }
}
