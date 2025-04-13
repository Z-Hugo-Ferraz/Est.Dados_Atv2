
public class Cidade {
    public String nome;
    public int tipo; // 0 - cidade, 1 - ligação
    public double dist;
    public double fTrafego;
    public int pedagio;
    public double tempo;
    public ListaDupla<Cidade> ligacoes;

    public Cidade(String nome, int tipo, double dist, double fTrafego, int pedagio) {
        this.nome = nome;
        this.tipo = tipo;
        if(tipo == 1){
        this.dist = dist;
        this.fTrafego = fTrafego;
        this.pedagio = pedagio;
        this.tempo = (dist*fTrafego) + (pedagio * 2);
        }else{ 
        this.ligacoes = new ListaDupla<>();
        }
    }

    public Cidade(String nome){
        this(nome,0,0.0,0.0,0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return nome.equals(((Cidade) obj).nome);
    }
    
    @Override
    public String toString() {
        if(this.tipo == 1){
        return " -> " + nome +
                " | Distância: " + dist +
                " | Fator de Tráfego: " + fTrafego +
                " | Pedágio: " + pedagio +
                " | Tempo: " + tempo + " min";
        }else{
            if(ligacoes == null){
                return "Cidade:" + nome + "\n" + "Sem ligações cadastradas!";
            }
            return "Cidade:" + nome + "\n" + ligacoes.toString();
        }
    }
}
