
[課題] カバレッジを計測したい!!


基本、アプリ作成する時は、ユニットテストを書きながら、コードを書いていきます。
動作確認しながらコードが書けるので作業がはかどります。

しかし、自分が書いたユニットテストが想定した通りのできか？うまくテストできているか？
を確認するすべはありません。

しかし、どの程度テストできているかを定量的に客観的に数値化する方法があれば、
テスト作成のど忘れとした時に、気がつくことができます。
これで、うまく想定したとおりにテストできていることが保障されるだろう。
実際のとこ、保障されないにしても心の安心を得ることはできます。


[カバレッジ]
 どの程度テストができているかを計る方法として、カバレッジという表現があります。
 この記事では、AndroidでCoverageをとる方法について解説します。

 #カバレッジとは何か？
  Coverageは、テストした時にどの程度網羅性があるかを数値します。
  具体的には、「テストできたクラスはのどのくらい？」「テストできたメソッドはどのくらい？」
 「テストできた行はどのくらい？」といった事を計測するのです。

 例えば、以下のようなコードがあったとして
 public void test(int a) {
   if(a>10) {
      --A--
   } else {
      --B--
   }
 }
 aが、11の時だけテストしたとすると、50%のテストができたといえます。
 aが、11の時と、aが10の時をテストした場合、100%のテストができたといえます。

 このように、自分が書いたコードがテストとできているかを、客観的にあらわす事ができるのです。
 これがCoverageです。


[EMMA]
 EMMAというツールを使うことで簡単にカバレッジをとる事ができます。
 EMMAはJavaのクラスファイルを書き換えます。
 すべてのif文の前後、メソッドの先頭に、Coverageを測定するためのコードを埋め込むのです。

 EMMAは単純に、埋め込んだコードの部分を記録しておき。アプリが終了するタイミングで、
 そのデータをストレージ上に記録します。


[EMMA Android]
 Androidには、EMMAがすでにSDKに含まれています。
 今回はそのまま、Androidに含まれている機能を使用することにしました。
 ※ 実現方法とかは機会があれば解説したいです。今回はしません。

 1. antからビルドできるようにする。
  ---> cd {ターゲットのprojectがある場所}
  ---> android.bat update project --path .
 2. テストプロジェクトともantからビルドできるようにす。
  ---> cd {ターゲットの test projectがある場所}
  ---> android update test-project -m <ターゲットのprojectの絶対パス> -p .
 3. emma用にAPKをビルドする。
  ---> cd {ターゲットのprojectがある場所}
  ---> ant emma debug install
 4 . テストプロジェクトもビルドする。
  ---> cd {ターゲットの test projectがある場所}
  ---> ant emma debug install test


後は結果がでます。


[成果物]
#サンプルのコードを書きました。
https://github.com/kyorohiro/KyoroSamples/tree/master/EmmaSample
 - runtest.sh
   テスト実施します。
 - createbuildxml.sh
   ビルド環境を作ります。

#以下のような、Coverageの結果が出力されます。
https://github.com/kyorohiro/KyoroSamples/tree/master/EmmaSample/tests/bin

[参考]
http://d.hatena.ne.jp/halts/20120201/p1
http://blog.pboos.ch/post/35269158339/android-coverage-report-for-unit-tests

