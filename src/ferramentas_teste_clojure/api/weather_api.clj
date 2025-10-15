(ns ferramentas-teste-clojure.api.weather-api)

(defn fetch-weather-data
  "ATENÇÃO: Simula uma chamada de API externa real e LENTA."
  [city]
  ;; A linha abaixo é para provar que esta função NUNCA será chamada nos testes.
  (println (str "\n!!! CHAMADA REAL À API para a cidade: " city " !!!\n"))
  (Thread/sleep 2000) ; Simula a lentidão da rede

  ;; Retorno simulado da API
  (cond
    (= city "São Paulo") {:city "São Paulo", :temp_c 14, :condition "Garoa"}
    (= city "Rio de Janeiro") {:city "Rio de Janeiro", :temp_c 30, :condition "Sol"}
    :else nil))
