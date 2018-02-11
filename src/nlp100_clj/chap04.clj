(ns nlp100-clj.chap04
  "第4章: 形態素解析

  メモ：MeCabではなくkuromojiを使用。
  "
  (:require [clojure.string :as str])
  (:import [com.atilika.kuromoji.ipadic Token Tokenizer]))


(def tokenizer (Tokenizer.))


(defn tokenize [s]
  (let [col (.tokenize tokenizer s)]
    (map #(apply str (.getSurface %1) "," (.getAllFeatures %1)) col)))


(defn init
  "夏目漱石の小説『吾輩は猫である』の文章（neko.txt）をkuromojiを使って形態素解析し，
  その結果をneko.txt.kuromojiというファイルに保存
  (オリジナルの問題はMeCabを使用しているが、Kuromojiを使用する）"

  []
  (let [tokens (flatten (map #(tokenize %) (str/split-lines (slurp "resources/neko.txt"))))
        s (str/join "\n" tokens)]
    (spit "resources/neko.txt.kuromoji" s)))


(defn p30
  "30. 形態素解析結果の読み込み

  形態素解析結果（neko.txt.kuromoji）を読み込むプログラムを実装せよ．
  ただし，各形態素は表層形（surface），基本形（base），品詞（pos），品詞細分類1（pos1）をキーとするマッピング型に格納し，
  1文を形態素（マッピング型）のリストとして表現せよ．第4章の残りの問題では，ここで作ったプログラムを活用せよ．
  "
  []
  (let [lines (->> (slurp "resources/neko.txt.kuromoji")
                   (str/split-lines )
                   (map #(str/split % #",")))]
    (map
     #(hash-map :surface (nth % 0)
                :pos     (nth % 1)
                :pos1    (nth % 2)
                :base    (nth % 7)
                )
         lines)))


(defn p31
  "31. 動詞

  動詞の表層形をすべて抽出せよ．
  "
  []
  (->> (filter #(= "動詞" (:pos %)) (p30))
       (map #(:surface %))))


(defn p32
  "32. 動詞の原形

  動詞の原形をすべて抽出せよ．"
  []
  (->> (filter #(= "動詞" (:pos %))(p30))
       (map #(:base %))))


(defn p33
  "33. サ変名詞

  サ変接続の名詞をすべて抽出せよ．"
  []
  (->> (filter #(= "サ変接続" (:pos1 %))(p30))
       (map #(:surface %))))
