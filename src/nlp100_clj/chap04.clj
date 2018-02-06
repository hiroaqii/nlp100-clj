(ns nlp100-clj.chap04
  "第4章: 形態素解析

  メモ：MeCabではなくkuromojiを使用。
  "
  (:require [clojure.string :as str])
  (:import [com.atilika.kuromoji.ipadic Token Tokenizer]))


(def tokenizer (Tokenizer.))


(defn tokenize [s]
  (let [col (.tokenize tokenizer s)]
    (map #(apply str (.getSurface %1) "\t" (.getAllFeatures %1)) col)))


(defn init
  "夏目漱石の小説『吾輩は猫である』の文章（neko.txt）をkuromojiを使って形態素解析し，
  その結果をneko.txt.kuromojiというファイルに保存
  (オリジナルの問題はMeCabを使用しているが、Kuromojiを使用する）"

  []
  (let [tokens (flatten (map #(tokenize %) (str/split-lines (slurp "resources/neko.txt"))))
        s (str/join "\n" tokens)]
    (spit "resources/neko.txt.kuromoji" s)))

;(init)
