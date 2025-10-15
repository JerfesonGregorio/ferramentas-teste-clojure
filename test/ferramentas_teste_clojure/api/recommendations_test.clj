(ns ferramentas-teste-clojure.api.recommendations-test
  (:require [clojure.test :refer :all]
            [midje.sweet :as test]
            [ferramentas-teste-clojure.api.recommendations :as recs]
            [ferramentas-teste-clojure.api.weather-api :as api]))

(test/fact "Testa a recomendação para um dia quente"
           ; ASSERÇÃO: Queremos que nossa função retorne "Dia de praia..."
           (recs/generate-recommendation "Rio de Janeiro") test/=> "Dia de praia em Rio de Janeiro!"

           ; PRÉ-CONDIÇÃO: ...CONTANTO QUE (provided) a chamada à API retorne 30 graus.
           (test/provided
             (api/fetch-weather-data "Rio de Janeiro") test/=> {:temp_c 30}))

(test/fact "Testa recomendação para um dia frio"
           ; ASSERÇÃO: A recomendação deve ser para levar um casaco.
           (recs/generate-recommendation "São Paulo") test/=> "Leve um casaco em São Paulo!"
           (test/provided
             (api/fetch-weather-data "São Paulo") test/=> {:temp_c 14}))

(test/fact "Testa a recomendação para um dia ameno"
      (recs/generate-recommendation "Brasília") test/=> "Tempo agradável em Brasília."
      (test/provided
        (api/fetch-weather-data "Brasília") test/=> {:temp_c 22}))

(test/fact "Testa a recomendação para um dia ameno"
           (recs/generate-recommendation "Brasília") test/=> "Tempo agradável em Brasília."
           (test/provided
             (api/fetch-weather-data "Brasília") test/=> {:temp_c 30}))

(test/fact "Testa o que acontece quando a API falha (retorna nil)"
      ; ASSERÇÃO: A função deve lidar graciosamente com a falha da API.
      (recs/generate-recommendation "Cidade Fictícia") test/=> "Não foi possível obter a previsão para Cidade Fictícia."

      ; PRÉ-CONDIÇÃO: Simulamos o caso em que a API não encontra a cidade.
      (test/provided
        (api/fetch-weather-data "Cidade Fictícia") test/=> nil))