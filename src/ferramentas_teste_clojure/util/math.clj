(ns ferramentas-teste-clojure.util.math)

(defn fatorial [n]
  (if (neg? n)
    nil
    ;else
    (reduce * (range 1 (inc n)))))

