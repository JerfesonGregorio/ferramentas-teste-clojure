(ns ferramentas-teste-clojure.util.demo)

(defn buggy-sort
  "Ordena uma coleção, mas se encontrar o número 7, captura a própria
  exceção e retorna a coleção original, não ordenada."
  [coll]
  (try
    ;; O bloco `try` contém o código que PODE lançar uma exceção.
    (if (some #{7} coll)
      (throw (Exception. "O número 7 me dá azar!"))
      (sort coll))

    ;; O bloco `catch` só é executado SE uma exceção for lançada no `try`.
    (catch Exception e
      ;; 'e' é a variável que contém o objeto da exceção.
      (println "Exceção capturada DENTRO da função:" (.getMessage e))

      ;; Em vez de quebrar, a função agora retorna a coleção original.
      coll)))

(defn format-user
  "Cria uma string de saudação para um usuário."
  [user-map]
  (str "Olá, " (:name user-map) " (ID: " (:id user-map) ")!"))

(defn calculate-cart-total
  "Calcula o valor total de um carrinho de compras."
  [cart]
  (reduce + (map :price (:items cart))))