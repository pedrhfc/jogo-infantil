package com.logica.atelier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Jogo {

    private static int i = 0;
    private final HashMap<String, ArrayList<String>> banco;
    private String palavra;
    private String palavraFormatada;
    private String erros;
    private String acertos;
    private int pontos;

    public Jogo() {
        this.palavra = new String();
        this.erros = new String();
        this.acertos = new String();
        this.banco = new HashMap<>();
        this.pontos = 0;
        this.carregarBanco();
    }

    /**
     * Carregar as palavras para a ArrayList banco.
     *
     */
    private void carregarBanco() {
        ArrayList<String> n1 = new ArrayList<>();
        ArrayList<String> n2 = new ArrayList<>();
        ArrayList<String> n3 = new ArrayList<>();
        n1.add("SOL");
        n1.add("BOTA");
        n1.add("BOLO");
        n1.add("CASA");
        n1.add("FLOR");
        n1.add("BOCA");
        n1.add("ALVO");
        n1.add("BALA");
        n1.add("PNEU");
        n1.add("MOTO");
        n1.add("DADO");// FIM NÃ�VEL 1 - 11 Imagens
        n2.add("CAVALO");
        n2.add("BATATA");
        n2.add("BONECA");
        n2.add("CELULAR");
        n2.add("LUVAS");
        n2.add("LEITE");
        n2.add("PEIXE");
        n2.add("ESQUILO");
        n2.add("PIZZA");
        n2.add("PONTE");
        n2.add("FAROL");
        n2.add("CANETA"); // FIM NÃ�VEL 2 - 12 Imagens
        n3.add("CHOCOLATE");
        n3.add("GALINHA");
        n3.add("COELHO");
        n3.add("CACHORRO");
        n3.add("CARRO");
        n3.add("TOALHA");
        n3.add("DINHEIRO");
        n3.add("FOLHA");
        n3.add("FLECHA");
        n3.add("ESPANTALHO");
        n3.add("CARROSSEL");
        n3.add("SERROTE");
        n3.add("CHUPETA");
        n3.add("TOCHA");
        n3.add("PASSARINHO"); // FIM NÃ�VEL 3 - 14 Imagens
        this.banco.put("nivel1", n1);
        this.banco.put("nivel2", n2);
        this.banco.put("nivel3", n3);
    }

    /**
     * Escolher uma palavra no banco de dados aleatoriamente e preencher o
     * atributo palavra com esse valor.
     *
     */
    public void sorteiaPalavra() {
        if (this.pontos <= 10) {   //Nivel 1: MÃ¡ximo de pontos: 10 12pontos - 4 tentativas
            Random sorteio = new Random();
            int numero_sorteado = sorteio.nextInt(this.banco.get("nivel1").size());
            this.palavra = (String) this.banco.get("nivel1").get(numero_sorteado);
            this.palavraFormatada = this.getPalavraFormatada().toUpperCase();
            this.acertos = new String();
            this.erros = new String();
            this.banco.get("nivel1").remove(numero_sorteado);

        }
        if (this.pontos > 10 && this.pontos <= 21) { //Nivel 2: MÃ¡ximo de pontos: 20 24pontos - 4 tentativas
            Random sorteio = new Random();
            int numero_sorteado = sorteio.nextInt(this.banco.get("nivel2").size());
            this.palavra = (String) this.banco.get("nivel2").get(numero_sorteado);
            this.palavraFormatada = this.getPalavraFormatada().toUpperCase();
            this.acertos = new String();
            this.erros = new String();
            this.banco.get("nivel2").remove(numero_sorteado);

        }
        if (this.pontos > 21 && this.pontos <= 36) { //Nivel 3: MÃ¡ximo de pontos: 35 39pontos - 4 tentativas
            Random sorteio = new Random();
            int numero_sorteado = sorteio.nextInt(this.banco.get("nivel3").size());
            this.palavra = (String) this.banco.get("nivel3").get(numero_sorteado);
            this.palavraFormatada = this.getPalavraFormatada().toUpperCase();
            this.acertos = new String();
            this.erros = new String();
            this.banco.get("nivel3").remove(numero_sorteado);
        }
    }

    /**
     * Receber o valor digitado pelo usuÃ¡rio e verificar se a letra pertence Ã 
     * palavra escolhida.
     *
     * @param letra
     *
     *
     */
    public void validaLetra(String letra) {
        letra = letra.substring(0, 1);
        letra = letra.toUpperCase();
        if (this.palavra.contains(letra)) {
            this.acertos = this.acertos.concat(letra).toUpperCase();
        } else {
            this.erros = this.erros.concat(letra).toUpperCase();
        }
    }

    /**
     * Realizar a substituiÃ§Ã£o do "-" pelas letras acertadas. Caso nÃ£o encontre
     * a palavra insere "-".
     *
     * @return
     *
     *
     */
    public String getPalavraFormatada() {
        String letra;
        this.palavraFormatada = "";
        for (int j = 0; j < this.palavra.length(); j++) {
            letra = this.palavra.substring(j, j + 1);
            letra = letra.toUpperCase();
            if (letra.equals(" ") || this.acertos.contains(letra)) {
                this.palavraFormatada = this.palavraFormatada.concat(letra);
            } else {
                this.palavraFormatada = this.palavraFormatada.concat("-");
            }
        }
        return this.palavraFormatada;
    }

    /**
     * Penalizar o usuÃ¡rio de acordo com o nÃºmero de erros.
     *
     */
    public void penalizarErros() {
        if (this.getErros().length() == 0) {
            pontos = this.getPontos() + 3;
            //Acertou de primeira
        }
        if (this.getErros().length() == 1 || this.getErros().length() == 2) {
            pontos = this.getPontos() + 2;
            //Acertou com 1 ou 2 erros
        }
        if (this.getErros().length() > 2) {
            pontos = this.getPontos() + 1;
            //Acertou com mais de 3 erros
        }
    }

    /**
     * Carregar o Ã¡udio das letras atravÃ©s de um file .wav descrito ao chamar o
     * mÃ©todo
     *
     * @param nomeAudio
     *
     *
     */
    public void bancoAudio(String nomeAudio) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nomeAudio).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Erro ao carregar Ã¡udio.");
        }
    }

    /**
     * Fechar o JFrame apÃ³s 7 segundos.
     *
     */
    public void timerSair() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                ++i;
                if (i == 7) {
                    System.exit(0);
                }
            }
        }, 1000, 1000);
    }

    /**
     * Verifica se o Jogador venceu.
     *
     * @return
     *
     *
     */
    public boolean isVencedor() {
        return this.palavraFormatada.replaceAll("-", "").equals(this.palavra.replaceAll("-", ""));
    }

    public String getErros() {
        return this.erros;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getAcertos() {
        return acertos;
    }

    /**
     * Retorna a imagem de acordo com a palavra sorteada
     *
     * @return
     *
     *
     */
    public String getImagem() {
        return "/com/ambiente/atelier/bancoImagens/" + this.palavra.toLowerCase() + ".png";
    }
}
