import java.awt.image.BufferStrategy;
import static javax.swing.JOptionPane.*;
import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;


public class Main {
    public static ListaDupla<Cidade> cidades = new ListaDupla<>();

    public static void main(String[] args) throws Exception {
        String opc;
        showMessageDialog(null, "Bem-vindo");
        String menu ="Escolha uma opção:\n" +
                    "1 - Cadastrar cidade \n" +
                    "2 - Cadastrar ligação \n" +
                    "3 - Listar\n" +
                    "4 - Buscar ligação\n" +
                    "5 - Busca por tempo\n" +
                    "6 - Sair";
        while(true){
            opc = showInputDialog(null, menu);
            switch (opc) {
                case "1":
                    cadCidade();
                    break;
                case"2":
                    cadLigacao();
                    break;
                case"3":
                    listagem();
                    break;
                case"4":
                    buscarLigacao();
                    break;
                case "5":
                    buscarTempo();
                    break;
                case "6":
                    showMessageDialog(null, "Saindo...");
                    return;
                default:
                    showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }   
    }

    private static Cidade cadCidade() {
        String nome = showInputDialog(null, "Insira o nome da cidade:");
        Cidade cidade = new Cidade(nome);
        cidades.add(cidade);
        return cidade;
    }

    private static void cadLigacao(){
        int op;
        Cidade cidadeO;
        double dist;
        double fTrafego;
        int pedagio;

        String nome = showInputDialog(null, "Insira o nome da cidade de origem:");
        if(cidades.buscar(new Cidade(nome)) == null){
            op = showConfirmDialog(null, "Cidade não encontrada! Deseja cadastrar?");
            if(op == YES_OPTION){
                cidadeO = cadCidade();
            }
            else{
                return;
            }   
        }else{
            cidadeO = cidades.buscar(new Cidade(nome)); 
        }

        nome = showInputDialog(null, "Insira o nome da cidade de destino");
        
        // Distancia
        while (true) {
            try {
                dist = parseDouble(showInputDialog(null, "Insira a distância:"));
                if(dist < 0){
                    showMessageDialog(null, "Distância não pode ser negativa!");
                    continue;
                }
                break;
            } catch(Exception e){
                showMessageDialog(null,"Utilize números!");
            } 
        }

        // Fator de Tráfego
        while (true) {
            try {
                fTrafego = parseDouble(showInputDialog(null, "Insira o fator de tráfego:"));
                if(fTrafego < 0 || fTrafego > 2){
                    showMessageDialog(null, "Fator Inválido!");
                    continue;
                }
                break;
            } catch(Exception e){
                showMessageDialog(null,"Utilize números!");
            } 
        }

        // Pedágio
        while (true) {
            try {
                pedagio = parseInt(showInputDialog(null, "Insira o número de pedagios:"));
                if(pedagio < 0){
                    showMessageDialog(null, "Quantidade de pedágios não pode ser negativa!");
                    continue;
                }
                break;
            } catch(Exception e){
                showMessageDialog(null,"Utilize números!");
            } 
        }

        cidadeO.ligacoes.add(new Cidade(nome, 1, dist, fTrafego, pedagio));
        
        op = showConfirmDialog(null, "O caminho oposto é válido?");
        if(op == YES_OPTION){
            Cidade cidadeD = new Cidade(nome);
            if(cidades.buscar(cidadeD) == null){
                cidades.add(cidadeD);
            }else{
                cidadeD = cidades.buscar(cidadeD); 
            }
            op = showConfirmDialog(null, "Deseja alterar o fator de tráfego e/ou o número de pedágios?");
            if(op == YES_OPTION){
                // Fator de Tráfego
                while (true) {
                    try {
                        fTrafego = parseDouble(showInputDialog(null, "Insira o fator de tráfego:"));
                        if(fTrafego < 0 || fTrafego > 2){
                            showMessageDialog(null, "Fator Inválido!");
                            continue;
                        }
                        break;
                    } catch(Exception e){
                        showMessageDialog(null,"Utilize números!");
                    } 
                }

                // Pedágio
                while (true) {
                    try {
                        pedagio = parseInt(showInputDialog(null, "Insira o número de pedagios:"));
                        if(pedagio < 0){
                            showMessageDialog(null, "Quantidade de pedágios não pode ser negativa!");
                            continue;
                        }
                        break;
                    } catch(Exception e){
                        showMessageDialog(null,"Utilize números!");
                    } 
                }
            }
            cidadeD.ligacoes.add(new Cidade(cidadeO.nome, 1, dist, fTrafego, pedagio));
        }

    }

    private static void listagem() {
        showMessageDialog(null, ""+cidades.toString());
    }

    private static void buscarLigacao() {
        String nome = showInputDialog(null, "Insira o nome da cidade de origem:");
        Cidade cidadeO = cidades.buscar(new Cidade(nome));
        if(cidadeO == null){
            showMessageDialog(null, "Cidade não encontrada!");
            return;
        }
        nome = showInputDialog(null, "Insira o nome da cidade de destino:");
        Cidade cidadeD = cidadeO.ligacoes.buscar(new Cidade(nome));
        if(cidadeD == null){
            showMessageDialog(null, "Ligação não encontrada!");
            return;
        }
        showMessageDialog(null, "Ligação encontrada:\n O tempo de entrega é de: " + cidadeD.tempo);
        
    }

    private static void buscarTempo(){
        double tempo;
        String msg ="As entregas que podem ser feitas neste tempo são:\n";
        // Tempo de entrega
        while (true) { 
            try {
                tempo = parseDouble(showInputDialog(null, "Insira o tempo de entrega:"));
                if(tempo < 0){
                    showMessageDialog(null, "Tempo não pode ser negativo!");
                    continue;
                }
                break;
            } catch (Exception e) {
                showMessageDialog(null, "Utilize números!");
            }
        }
        
        NoDuplo<Cidade> aux1 = cidades.first;
        
        if(aux1 == null){
            showMessageDialog(null, "Nenhuma cidade cadastrada!");
            return;
        }
        
        // Acha cidades com ligações
        while(aux1 != null){
        
            if(aux1.getDado().ligacoes == null){
                aux1 = aux1.getProx();
                continue;
            }
        
            NoDuplo<Cidade> aux2 = aux1.getDado().ligacoes.first;

            // Comapara o tempo de entrega de todas ligações
            while(aux2 != null){
                if(aux2.getDado().tempo <= tempo){
                    msg += aux1.getDado().nome + " -> " + aux2.getDado().nome + ": "+ aux2.getDado().tempo + " min\n";
                }
                aux2 = aux2.getProx();
            }

            aux1 = aux1.getProx();
        }
    
        if(msg.equals("As entregas que podem ser feitas neste tempo são:\n"))
            showMessageDialog(null, "Nenhuma entrega pode ser feita neste tempo!");
        else
            showMessageDialog(null, msg);
    }
}
