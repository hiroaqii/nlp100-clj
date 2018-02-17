(ns nlp100-clj.chap05

  "第5章: 係り受け解析"
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))


(defn init
  "夏目漱石の小説『吾輩は猫である』の文章（neko.txt）をCaboChaを使って係り受け解析し，
  その結果をneko.txt.cabochaというファイルに保存せよ．このファイルを用いて，以下の問に対応するプログラムを実装せよ．"
  []
  (let [s (:out (shell/sh "cabocha" "-f1" "resources/neko.txt"))]
    (spit "resources/neko.txt.cabocha" s)))


