package org.example;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;

public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        String enderecourl = "https://v6.exchangerate-api.com/v6/54a7f2507ca2dd3efe90f930/latest/USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(enderecourl))
                .build();

        JsonObject jsonObject;
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

        List<Map<String, Object>> convertores = new ArrayList<>();


        try{
            System.out.println("Digite o valor da moeda escolhida:");
            double valor = Double.parseDouble(scanner.nextLine());

            System.out.println("Digite o simbolo da moeda escolhida(EX:eal (BRL); Dólar Americano (USD),Euro (EUR); e etc ... ");
            var simbolo1 = scanner.nextLine();

            double moeda1 = jsonObject.getAsJsonObject("conversion_rates").get(simbolo1).getAsDouble();

            System.out.println("Digite o segundo simbolo da moeda escolhida(EX:eal (BRL); Dólar Americano (USD),Euro (EUR); e etc ... ");
            var simbolo2 = scanner.nextLine();
            double moeda2 = jsonObject.getAsJsonObject("conversion_rates").get(simbolo2).getAsDouble();


            converter(valor,simbolo1,simbolo2,moeda1,moeda2);

            Map<String, Object> convertore = new HashMap<>();
            convertore.put("Moeda_Primairia: ",simbolo1);
            convertore.put("Primeiro-valor: ",valor);
            convertore.put("Moeda-Secundaria: ",simbolo2);
            convertore.put("Valor-Convertido: ", Math.round((valor * moeda2 / moeda1) * 100.0) / 100.0);

            convertores.add(convertore);


        }catch(Exception e){
            System.out.println("Ocorreu um erro ao inserir valores!");
        }


    }


    public static void converter(double valor, String simbolo1, String simbolo2, double moeda1, double moeda2) {
        System.out.println(valor + " em " + simbolo1 + " equivale a " + (valor * (moeda2 / moeda1)) + " em " + simbolo2);
    }


}