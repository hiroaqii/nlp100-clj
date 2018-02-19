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


;; p40

(defrecord Morph [surface base ops ops1])

(defn line->Marph [s]
  (let [lst (str/split s #"\t")
        surface (first lst)
        other (str/split (second lst) #",")]
    (->Morph surface (nth other 6) (first other) (second other))))

(defn p40
  "40. 係り受け解析結果の読み込み（形態素）

  形態素を表すクラスMorphを実装せよ．
  このクラスは表層形（surface），基本形（base），品詞（pos），品詞細分類1（pos1）をメンバ変数に持つこととする．
  さらに，CaboChaの解析結果（neko.txt.cabocha）を読み込み，各文をMorphオブジェクトのリストとして表現し，3文目の形態素列を表示せよ．"
  []
  (let [lst (->> (slurp "resources/neko.txt.cabocha")
                 (str/split-lines)
                 (filter #(not (str/starts-with? % "*")))
                 (partition-by #(= "EOS" %))
                 (filter #(not= "EOS" (first %)) ))
        marph-lst (map #(map line->Marph %) lst)]
    (nth marph-lst 2)))



;; p41

(defrecord Chunk [morphs dst srcs])

(defn str->dst-map
  " 文節番号と係り先の文節番号をmap形式で返却
  例： * 6 7D 0/2 -1.148488 -> {6 7} "
  [s]
  (let [lst (str/split s #" ")
        src (second lst)
        dst (str/replace (nth lst 2) #"D" "")]
    (hash-map src dst)))

(defn dst-map->src-map
  "keyとvalを入れ替え、同一のvalをkeyに纏めたmapを作成
  例：{0 5, 1 2, 2 3, 3 4, 4 5, 5 -1} -> {5 [0 4], 2 [1], 3 [2], 4 [3], -1 [5]}"
  [m]
  (reduce #(merge-with into %1 {(val %2) [(key %2)]}) {} m))


