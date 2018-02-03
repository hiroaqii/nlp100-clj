(ns nlp100-clj.chap02
  "第3章: 正規表現"
  (:require [clojure.string :as str]
            [cheshire.core :as json]))

(def file-path "resources/jawiki-country.json")

(defn p20
  "20. JSONデータの読み込み

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


(defn p24
  "24. ファイル参照の抽出

  記事から参照されているメディアファイルをすべて抜き出せ．"
  []
  (map second (re-seq #"[ファイル|File]:(.+?)\|" eng-text)))


(def template (second (re-find #"\{\{基礎情報 国\n\|([\s\S]*\n)\}\}\n" eng-text)))


(defn p25
  "25. テンプレートの抽出

  記事中に含まれる「基礎情報」テンプレートのフィールド名と値を抽出し，辞書オブジェクトとして格納せよ．"
  ([](p25 template))
  ([s]
   (->> (str/split s #"\n\|")
        (map #(str/split % #" = "))
        (into {}))))


(defn p26
  "26. 強調マークアップの除去

  25の処理時に，テンプレートの値からMediaWikiの強調マークアップ
 （弱い強調，強調，強い強調のすべて）を除去してテキストに変換せよ"
  ([](p26 template))
  ([s] (p25 (str/replace s #"('{5}|'{2,3})(.+?)\1" "$2"))))


(defn p27
  "27. 内部リンクの除去

  26の処理に加えて，テンプレートの値からMediaWikiの内部リンクマークアップを除去し，テキストに変換せよ"
  ([] (p27 template))
  ([s] (let [s (-> (str/replace s #"(\[\[.*?)\#.*?\|?(.*\]\])" "$1$2") ; [[記事名#節名|表示文字]] -> [[記事名|表示文字]]
                   (str/replace #"(\[\[.*?)\|.+(\]\])" "$1$2")         ; [[記事名|表示文字]]      -> [[記事名]]
                   (str/replace #"\[\[(.+)\]\]" "$1"))]                ; [[記事名]]               -> 記事名
         (p26 s))))


(defn p28
  "28. MediaWikiマークアップの除去

  27の処理に加えて，テンプレートの値からMediaWikiマークアップを可能な限り除去し，国の基本情報を整形せよ．"
  ([] (p28 template))
  ([s] (let [a (-> (str/replace s #"\[https?://.+\]" "") ;[http://www.example.org 表示文字] -> ""
                   (str/replace #"https?://[\w/:%#\$&\?\(\)~\.=\+\-]" "")
                   (str/replace #"<.+?>" ""))]
         (p27 a))))
