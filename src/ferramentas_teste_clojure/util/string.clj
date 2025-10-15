(ns ferramentas-teste-clojure.util.string)

(defn maiuscula?
  "Verifica se uma string está totalmente em maiúsculas."
  [s]
  (if (string? s)
    (= s (.toUpperCase s))
    false))

(defn inverter
  "Inverte uma string."
  [s]
  (clojure.string/reverse s))
