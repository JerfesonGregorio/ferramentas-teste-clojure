(ns ferramentas-teste-clojure.util.math-test
  (:require [clojure.test :as t]
            [ferramentas-teste-clojure.util.math :as math-util]))

;=============================
;       CLOJURE.TEST
;=============================

(defn uma-vez-por-namespace-fixture [f]
  (println "=> SETUP GERAL: Iniciando suíte de testes de MATEMÁTICA (uma única vez)")
  (try
    (f) ; Executa TODOS os testes do namespace
    (finally
      (println "<= TEARDOWN GERAL: Finalizando suíte de testes de MATEMÁTICA (uma única vez)"))))

;; Registra a fixture para ser executada UMA ÚNICA VEZ para todo o namespace
(t/use-fixtures :once uma-vez-por-namespace-fixture)

(t/deftest fatorial-test
  (t/testing "Cálculo de fatorial para casos padrão"
    (t/is (= 1 (math-util/fatorial 0)) "Fatorial de 0 é 1")
    (t/is (= 1 (math-util/fatorial 1)) "Fatorial de 1 é 1")
    (t/is (= 1 (math-util/fatorial 4)) "Erro proposital")
    (t/is (= 1 (math-util/fatorial 3)) "Erro proposital")
    (t/is (= 120 (math-util/fatorial 5)) "Fatorial de 5 é 120")))

(t/deftest fatorial-casos-de-borda-test
  (t/testing "Cálculo de fatorial para casos de borda"
    (t/is (nil? (math-util/fatorial -5)) "Fatorial de negativos devem ser nil")))

(t/deftest soma-com-are-test
         (t/testing "Testando a função soma com múltiplos valores"
                  (t/are [resultado-esperado x y] (= resultado-esperado (math-util/soma x y))
                       ;; Cada linha abaixo é um conjunto de dados para [resultado-esperado x y]
                       4    2    2
                       0   -1    1
                       100  50   50
                       -5  -2   -3
                       99   99   0
                       )))

(t/run-test soma-com-are-test) ; Roda teste específico
(t/run-all-tests) ; Roda todos os testes referentes a uma regex. Sem regex roda todos os testes por default.

(t/run-tests) ; Roda testes de namespaces específicos
;; user=> (clojure.test/run-tests 'exemplo-testes.util.math-test 'exemplo-testes.util.string-test)
