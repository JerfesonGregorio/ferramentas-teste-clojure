(ns ferramentas-teste-clojure.api.recommendations
  (:require [ferramentas-teste-clojure.api.weather-api :as api]))

(defn generate-recommendation
  "Usa os dados do tempo para gerar uma recomendação para o usuário."
  [city]
  (if-let [weather-data (api/fetch-weather-data city)]
    (let [temp (:temp_c weather-data)]
      (cond
        (>= temp 25) (str "Dia de praia em " city "!")
        (> temp 15) (str "Tempo agradável em " city ".")
        :else (str "Leve um casaco em " city "!")))
    ;; Caso a API não retorne dados
    (str "Não foi possível obter a previsão para " city ".")))

(defn get-recommendation-details
  "Retorna um mapa com a recomendação e os dados originais da API."
  [city]
  (when-let [weather-data (api/fetch-weather-data city)]
    {:recommendation (generate-recommendation city)
     :source-data weather-data}))
