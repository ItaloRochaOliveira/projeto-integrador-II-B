package db.mock;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import db.enitites.PontoColeta;

public class PontoColetaDataMock {
    private PontoColeta pontoColeta;

    public PontoColeta getPontoColeta() {
        return pontoColeta;
    }

    public PontoColetaDataMock(int id, String nome, String endereco, String cidade, String estado, String horarioFuncionamento,
                              List<String> tiposResiduos, double latitude, double longitude,
                              Date created_at, Date updated_at, Date deleted_at, boolean ativo) {
        pontoColeta = new PontoColeta(id, nome, endereco, cidade, estado, horarioFuncionamento, tiposResiduos,
                                     latitude, longitude, created_at, updated_at, deleted_at, ativo);
    }

    
    public static final List<String> TIPOS_RESIDUOS_DISPONIVEIS = Arrays.asList(
        "plastico", "vidro", "metal", "papel", "eletronico", "oleo"
    );

    
    private static PontoColetaDataMock mk(int id, String nome, String endereco, String cidade, String estado, String horario,
            List<String> tipos, double lat, double lng) {
        long now = System.currentTimeMillis();
        return new PontoColetaDataMock(id, nome, endereco, cidade, estado, horario, tipos, lat, lng,
                new Date(now), new Date(now), null, true);
    }

    
    private static List<PontoColetaDataMock> buildPontos() {
        List<PontoColetaDataMock> list = new ArrayList<>();
        int i = 1;

        
        list.add(mk(i++, "EcoPonto Rio Branco Centro", "Rua Epaminondas Jácome, 100", "Rio Branco", "AC", "08:00-18:00", Arrays.asList("plastico", "vidro", "papel"), -9.9754, -67.8243));
        list.add(mk(i++, "Coleta Cruzeiro do Sul", "Av. Rodrigues Alves, 200", "Cruzeiro do Sul", "AC", "08:00-17:00", Arrays.asList("metal", "oleo"), -7.6280, -72.6706));

        
        list.add(mk(i++, "EcoPonto Maceió Pajuçara", "Av. Dr. Antônio Gouveia, 50", "Maceió", "AL", "09:00-18:00", Arrays.asList("plastico", "vidro", "metal"), -9.6658, -35.7350));
        list.add(mk(i++, "EcoPonto Arapiraca", "Rua Domingos Correia, 30", "Arapiraca", "AL", "08:00-17:00", Arrays.asList("papel", "eletronico"), -9.7549, -36.6615));

        
        list.add(mk(i++, "Recicla Manaus Centro", "Av. Eduardo Ribeiro, 500", "Manaus", "AM", "08:00-18:00", Arrays.asList("plastico", "vidro", "papel"), -3.1316, -60.0233));
        list.add(mk(i++, "EcoPonto Parintins", "Rua Jonathas Pedrosa, 120", "Parintins", "AM", "08:00-16:00", Arrays.asList("metal", "oleo"), -2.6283, -56.7351));

        
        list.add(mk(i++, "EcoPonto Macapá", "Rua Cândido Mendes, 210", "Macapá", "AP", "08:00-18:00", Arrays.asList("plastico", "papel"), 0.0356, -51.0705));
        list.add(mk(i++, "EcoPonto Santana", "Av. Santana, 400", "Santana", "AP", "08:00-17:00", Arrays.asList("vidro", "eletronico"), -0.0588, -51.1817));

        
        list.add(mk(i++, "EcoPonto Salvador Barra", "Av. Oceânica, 1000", "Salvador", "BA", "08:00-19:00", Arrays.asList("plastico", "vidro", "metal", "papel"), -13.0100, -38.5326));
        list.add(mk(i++, "Coleta Feira de Santana", "Av. Getúlio Vargas, 300", "Feira de Santana", "BA", "08:00-17:00", Arrays.asList("eletronico", "oleo"), -12.2579, -38.9649));

        
        list.add(mk(i++, "EcoPonto Fortaleza Meireles", "Av. Abolição, 1500", "Fortaleza", "CE", "08:00-18:00", Arrays.asList("plastico", "vidro"), -3.7305, -38.5218));
        list.add(mk(i++, "EcoPonto Juazeiro do Norte", "Rua São Pedro, 250", "Juazeiro do Norte", "CE", "08:00-17:00", Arrays.asList("metal", "papel"), -7.2130, -39.3155));

        
        list.add(mk(i++, "EcoPonto Brasília Asa Sul", "SQS 308, Bloco A", "Brasília", "DF", "08:00-18:00", Arrays.asList("plastico", "papel", "eletronico"), -15.8267, -47.9218));
        list.add(mk(i++, "EcoPonto Taguatinga", "QNJ 23, Lote 10", "Taguatinga", "DF", "08:00-17:00", Arrays.asList("vidro", "oleo"), -15.8350, -48.0560));

        
        list.add(mk(i++, "EcoPonto Vitória Praia do Canto", "Rua Aleixo Neto, 50", "Vitória", "ES", "08:00-18:00", Arrays.asList("plastico", "metal"), -20.2976, -40.2958));
        list.add(mk(i++, "EcoPonto Vila Velha", "Av. Champagnat, 220", "Vila Velha", "ES", "08:00-17:00", Arrays.asList("vidro", "papel"), -20.3365, -40.2927));

        
        list.add(mk(i++, "EcoPonto Goiânia Bueno", "Av. T-63, 100", "Goiânia", "GO", "08:00-18:00", Arrays.asList("plastico", "eletronico"), -16.6869, -49.2648));
        list.add(mk(i++, "EcoPonto Anápolis", "Av. Universitária, 300", "Anápolis", "GO", "08:00-17:00", Arrays.asList("metal", "oleo"), -16.3281, -48.9534));

        
        list.add(mk(i++, "EcoPonto São Luís Renascença", "Av. Colares Moreira, 150", "São Luís", "MA", "08:00-18:00", Arrays.asList("plastico", "vidro"), -2.5299, -44.3028));
        list.add(mk(i++, "EcoPonto Imperatriz", "Rua Davi Alves Silva, 400", "Imperatriz", "MA", "08:00-17:00", Arrays.asList("papel", "eletronico"), -5.5255, -47.4742));

        
        list.add(mk(i++, "EcoPonto BH Savassi", "Rua Pernambuco, 900", "Belo Horizonte", "MG", "08:00-19:00", Arrays.asList("plastico", "vidro", "papel"), -19.9320, -43.9378));
        list.add(mk(i++, "EcoPonto Uberlândia", "Av. Rondon Pacheco, 800", "Uberlândia", "MG", "08:00-17:00", Arrays.asList("metal", "eletronico"), -18.9141, -48.2749));

        
        list.add(mk(i++, "EcoPonto Campo Grande Centro", "Rua 14 de Julho, 1200", "Campo Grande", "MS", "08:00-18:00", Arrays.asList("plastico", "papel"), -20.4697, -54.6201));
        list.add(mk(i++, "EcoPonto Dourados", "Av. Marcelino Pires, 1000", "Dourados", "MS", "08:00-17:00", Arrays.asList("vidro", "oleo"), -22.2231, -54.8120));

        
        list.add(mk(i++, "EcoPonto Cuiabá Centro", "Av. Getúlio Vargas, 1500", "Cuiabá", "MT", "08:00-18:00", Arrays.asList("plastico", "metal"), -15.6010, -56.0974));
        list.add(mk(i++, "EcoPonto Rondonópolis", "Av. Fernando Corrêa, 700", "Rondonópolis", "MT", "08:00-17:00", Arrays.asList("vidro", "papel"), -16.4673, -54.6372));

        
        list.add(mk(i++, "EcoPonto Belém Nazaré", "Av. Nazaré, 500", "Belém", "PA", "08:00-18:00", Arrays.asList("plastico", "eletronico"), -1.4558, -48.4902));
        list.add(mk(i++, "EcoPonto Santarém", "Av. Tapajós, 300", "Santarém", "PA", "08:00-17:00", Arrays.asList("metal", "oleo"), -2.4375, -54.6996));

        
        list.add(mk(i++, "EcoPonto João Pessoa Tambaú", "Av. Almirante Tamandaré, 100", "João Pessoa", "PB", "08:00-18:00", Arrays.asList("plastico", "vidro"), -7.1153, -34.8610));
        list.add(mk(i++, "EcoPonto Campina Grande", "Av. Floriano Peixoto, 600", "Campina Grande", "PB", "08:00-17:00", Arrays.asList("papel", "eletronico"), -7.2291, -35.8808));

        
        list.add(mk(i++, "EcoPonto Recife Boa Viagem", "Av. Boa Viagem, 2000", "Recife", "PE", "08:00-18:00", Arrays.asList("plastico", "metal"), -8.1188, -34.9033));
        list.add(mk(i++, "EcoPonto Caruaru", "Av. Agamenon Magalhães, 400", "Caruaru", "PE", "08:00-17:00", Arrays.asList("vidro", "papel"), -8.2845, -35.9699));

        
        list.add(mk(i++, "EcoPonto Teresina Centro", "Av. Frei Serafim, 1200", "Teresina", "PI", "08:00-18:00", Arrays.asList("plastico", "papel"), -5.0892, -42.8016));
        list.add(mk(i++, "EcoPonto Parnaíba", "Av. São Sebastião, 300", "Parnaíba", "PI", "08:00-17:00", Arrays.asList("vidro", "eletronico"), -2.9059, -41.7766));

        
        list.add(mk(i++, "EcoPonto Curitiba Batel", "Av. do Batel, 1000", "Curitiba", "PR", "08:00-18:00", Arrays.asList("plastico", "vidro", "metal"), -25.4433, -49.2800));
        list.add(mk(i++, "EcoPonto Londrina", "Av. Higienópolis, 500", "Londrina", "PR", "08:00-17:00", Arrays.asList("papel", "eletronico"), -23.3103, -51.1628));

        
        list.add(mk(i++, "EcoPonto Rio Copacabana", "Av. Atlântica, 1700", "Rio de Janeiro", "RJ", "08:00-19:00", Arrays.asList("plastico", "vidro", "papel"), -22.9711, -43.1822));
        list.add(mk(i++, "EcoPonto Niterói Icaraí", "Rua Lopes Trovão, 200", "Niterói", "RJ", "08:00-17:00", Arrays.asList("metal", "eletronico"), -22.9053, -43.1060));

        
        list.add(mk(i++, "EcoPonto Natal Ponta Negra", "Av. Eng. Roberto Freire, 900", "Natal", "RN", "08:00-18:00", Arrays.asList("plastico", "vidro"), -5.8783, -35.1631));
        list.add(mk(i++, "EcoPonto Mossoró", "Av. João da Escóssia, 400", "Mossoró", "RN", "08:00-17:00", Arrays.asList("papel", "oleo"), -5.1864, -37.3443));

        
        list.add(mk(i++, "EcoPonto Porto Velho", "Av. Sete de Setembro, 700", "Porto Velho", "RO", "08:00-18:00", Arrays.asList("plastico", "metal"), -8.7619, -63.9039));
        list.add(mk(i++, "EcoPonto Ji-Paraná", "Av. Marechal Rondon, 300", "Ji-Paraná", "RO", "08:00-17:00", Arrays.asList("vidro", "papel"), -10.8777, -61.9327));

        
        list.add(mk(i++, "EcoPonto Boa Vista Centro", "Av. Capitão Júlio Bezerra, 150", "Boa Vista", "RR", "08:00-18:00", Arrays.asList("plastico", "papel"), 2.8197, -60.6733));
        list.add(mk(i++, "EcoPonto Rorainópolis", "Av. Principal, 200", "Rorainópolis", "RR", "08:00-17:00", Arrays.asList("vidro", "eletronico"), 0.9392, -60.4389));

        
        list.add(mk(i++, "EcoPonto Porto Alegre Moinhos", "Rua Padre Chagas, 300", "Porto Alegre", "RS", "08:00-18:00", Arrays.asList("plastico", "vidro", "metal"), -30.0277, -51.2287));
        list.add(mk(i++, "EcoPonto Caxias do Sul", "Rua Sinimbu, 500", "Caxias do Sul", "RS", "08:00-17:00", Arrays.asList("papel", "oleo"), -29.1678, -51.1794));

        
        list.add(mk(i++, "EcoPonto Florianópolis Centro", "Rua Felipe Schmidt, 600", "Florianópolis", "SC", "08:00-18:00", Arrays.asList("plastico", "papel"), -27.5949, -48.5482));
        list.add(mk(i++, "EcoPonto Joinville", "Rua Princesa Isabel, 250", "Joinville", "SC", "08:00-17:00", Arrays.asList("vidro", "eletronico"), -26.3044, -48.8487));

        
        list.add(mk(i++, "EcoPonto Aracaju Atalaia", "Av. Santos Dumont, 1200", "Aracaju", "SE", "08:00-18:00", Arrays.asList("plastico", "vidro"), -10.9472, -37.0511));
        list.add(mk(i++, "EcoPonto Itabaiana", "Av. Ivo de Souza Prado, 400", "Itabaiana", "SE", "08:00-17:00", Arrays.asList("papel", "metal"), -10.6859, -37.4273));

        
        list.add(mk(i++, "EcoPonto São Paulo Pinheiros", "Rua dos Pinheiros, 321", "São Paulo", "SP", "24 horas", Arrays.asList("plastico", "vidro", "metal", "papel", "eletronico", "oleo"), -23.5629, -46.7009));
        list.add(mk(i++, "EcoPonto Campinas Cambuí", "Rua Coronel Quirino, 800", "Campinas", "SP", "08:00-18:00", Arrays.asList("eletronico", "oleo"), -22.9009, -47.0573));

        
        list.add(mk(i++, "EcoPonto Palmas Plano Diretor", "Av. JK, 1500", "Palmas", "TO", "08:00-18:00", Arrays.asList("plastico", "papel"), -10.1840, -48.3336));
        list.add(mk(i++, "EcoPonto Araguaína", "Av. Filadélfia, 700", "Araguaína", "TO", "08:00-17:00", Arrays.asList("vidro", "metal"), -7.1924, -48.2044));

        return list;
    }

    public static final PontoColetaDataMock[] PONTOS_COLETA = buildPontos().toArray(new PontoColetaDataMock[0]);
}
