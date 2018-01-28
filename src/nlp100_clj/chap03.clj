(ns nlp100-clj.chap02
  "第3章: 正規表現"
  (:require [clojure.string :as str]
            [cheshire.core :as json]))

(def file-path "resources/jawiki-country.json")

(defn p20
  "JSONデータの読み込み

  Wikipedia記事のJSONファイルを読み込み，「イギリス」に関する記事本文を表示せよ．
  問題21-29では，ここで抽出した記事本文に対して実行せよ．"
  []
  (let [lines (str/split-lines (slurp file-path))
        jsons (map #(json/parse-string %) lines)
        m (first (filter #(= (% "title") "イギリス") jsons))]
    (m "text")))

(def eng-text (p20))

(defn p21
  "21. カテゴリ名を含む行を抽出

  記事中でカテゴリ名を宣言している行を抽出せよ"
  []
  (->> (re-seq #"\[\[Category:.+?\]\]" eng-text)))


(defn p22
  "22. カテゴリ名の抽出

  記事のカテゴリ名を（行単位ではなく名前で）抽出せよ "
  []
  (->> (re-seq #"\[\[Category:(.+?)\]\]" eng-text)
       (map second)))


(defn p23
  "23. セクション構造

  記事中に含まれるセクション名とそのレベル（例えば== セクション名 ==なら1）を表示せよ．"
  []
  (->> (re-seq #"(={2,})\s*(.+?)\s*\1" eng-text)
       (map #(list (last %) (dec (count (second %)))))))
