(ns ferramentas-teste-clojure.util.demo-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ferramentas-teste-clojure.util.demo :as demo]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]))

;; --- 1. Teste de propriedade simples que PASSA ---

;; Gerador para um mapa de usuário
(def user-gen
  (gen/hash-map :id   (gen/such-that pos? gen/int)
                :name (gen/such-that not-empty gen/string-alphanumeric)))

(defspec user-format-always-contains-id 100
  (prop/for-all [user user-gen]
    ;; PROPRIEDADE: A string formatada SEMPRE contém o ID do usuário.
    (let [formatted-str (demo/format-user user)]
      (str/includes? formatted-str (str (:id user))))))

;; --- 2. Teste de propriedade de um carrinho (gerador complexo) que PASSA ---

;; Gerador para um item de compra com preço positivo
(def item-gen
  (gen/hash-map :name  gen/string-alphanumeric
                 :price (gen/fmap bigdec (gen/double* {:min 0.01 :max 1000 :NaN? false :infinite? false}))))

;; Gerador para um carrinho que contém entre 1 e 10 itens
(def cart-gen
  (gen/hash-map :items (gen/vector item-gen 1 10)))

(defspec cart-total-is-always-positive 50
  (prop/for-all [cart cart-gen]
    ;; PROPRIEDADE: O total do carrinho nunca deve ser negativo.
    (>= (demo/calculate-cart-total cart) 0)))

;; --- 3. Teste de propriedade que FALHA para encontrar nosso bug ---

(defspec buggy-sort-is-always-ordered 200
  (prop/for-all [v (gen/vector gen/int)]
    ;; PROPRIEDADE: Uma lista ordenada é uma lista onde cada elemento
    ;; é menor ou igual ao próximo.
    (let [s (demo/buggy-sort v)]
      (or (empty? s) (apply <= s)))))