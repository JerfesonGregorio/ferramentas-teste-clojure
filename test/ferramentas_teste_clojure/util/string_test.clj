(ns ferramentas-teste-clojure.util.string-test
  (:require [clojure.test :as t]
            [ferramentas-teste-clojure.util.string :as str-util]))

;=============================
;       CLOJURE.TEST
;=============================

(defn para-cada-teste-fixture [f]
  (println "-> SETUP: Preparando para um teste de STRING...")
  (try
    (f) ; executa o teste
    (finally
      (println "<- TEARDOWN: Limpando após um teste de STRING..."))))

;; Registra a fixture para ser executada para CADA deftest neste namespace
(t/use-fixtures :each para-cada-teste-fixture)

(t/deftest maiuscula-test
  (t/testing "Verificação de strings maiúsculas"
    (t/is (true? (str-util/maiuscula? "OLA MUNDO")))
    (t/is (true? (str-util/maiuscula? "Ola mundo"))) ;; Falha proposital
    (t/is (false? (str-util/maiuscula? "ola mundo")))))

(t/deftest maiuscula-com-entradas-invalidas-test
  (t/testing "Comportamentos de entradas que não são strings"
    (t/is (false? (str-util/maiuscula? nil)) "Deveria retornar falso para nil")
    (t/is (false? (str-util/maiuscula? 123)) "Deveria retornar falso para números")))

(t/deftest inverter-test
  (t/testing "Inversão de strings"
    (t/is (= "erujolc" (str-util/inverter "clojure")))
    (t/is (= "" (str-util/inverter "")))))
