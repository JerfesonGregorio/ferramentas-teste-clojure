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
             (api/fetch-weather-data "Brasília") test/=> {:temp_c 22}))

(test/fact "Testa o que acontece quando a API falha (retorna nil)"
      ; ASSERÇÃO: A função deve lidar graciosamente com a falha da API.
      (recs/generate-recommendation "Cidade Fictícia") test/=> "Não foi possível obter a previsão para Cidade Fictícia."

      ; PRÉ-CONDIÇÃO: Simulamos o caso em que a API não encontra a cidade.
      (test/provided
        (api/fetch-weather-data "Cidade Fictícia") test/=> nil))

;; ====================================================================
;;  EXEMPLOS COM CHECKERS (VERIFICADORES)
;; ====================================================================

(test/fact "Checkers para a recomendação em string"
      (recs/generate-recommendation "São Paulo") test/=> (test/contains #"^Leve um casaco")
      (test/provided
        (api/fetch-weather-data "São Paulo") test/=> {:temp_c 14}))

(test/fact "Checkers para a estrutura de dados detalhada"
           (recs/get-recommendation-details "Rio de Janeiro") test/=> (test/just {:recommendation (test/contains #"!$")
                                                                                  :source-data (test/just {:city "Rio de Janeiro"
                                                                                                           :temp_c number?})})
      (test/provided
        (api/fetch-weather-data "Rio de Janeiro") test/=> {:temp_c 30, :city "Rio de Janeiro"}))

;; ====================================================================
;;  EXEMPLO COM BACKGROUND (FIXTURES)
;; ====================================================================

;; `background` define uma pré-condição que se aplica a TODOS os `fact`s
;; subsequentes neste bloco. Isso evita repetir o mesmo `provided`.
(test/background (api/fetch-weather-data "Curitiba") test/=> {:temp_c 10, :condition "Chuva"})

(test/fact "BACKGROUND: A recomendação para Curitiba deve ser sobre o frio"
      ;; Este teste usa o mock definido no `background` acima.
      ;; Não precisamos de um `provided` aqui.
      (recs/generate-recommendation "Curitiba") test/=> "Leve um casaco em Curitiba!")

(test/fact "BACKGROUND: Os detalhes para Curitiba devem conter a temperatura correta"
      ;; Este teste TAMBÉM usa o mesmo mock do `background`.
      (recs/get-recommendation-details "Curitiba") test/=> (test/contains {:source-data (test/contains {:temp_c 10})}))
