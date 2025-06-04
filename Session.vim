let SessionLoad = 1
let s:so_save = &g:so | let s:siso_save = &g:siso | setg so=0 siso=0 | setl so=-1 siso=-1
let v:this_session=expand("<sfile>:p")
silent only
silent tabonly
cd ~/projects/scala-projects/nrftw-toolkit
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
let s:shortmess_save = &shortmess
if &shortmess =~ 'A'
  set shortmess=aoOA
else
  set shortmess=aoO
endif
badd +1 ~/projects/scala-projects/nrftw-toolkit
badd +15 src/main/scala/com/tewe/nrftw/ItemBuilder.scala
badd +109 src/main/scala/com/tewe/nrftw/Items.scala
badd +122 src/main/scala/com/tewe/nrftw/Main.scala
badd +66 style.css
badd +29 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/collection/mutable/LinkedHashMap.scala
badd +43 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/collection/BuildFrom.scala
badd +391 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/collection/Factory.scala
badd +1 .gitignore
badd +100 src/main/scala/com/tewe/nrftw/WeaponBuilder.scala
badd +9 package.json
badd +54 README.md
badd +1 src/main/scala/com/tewe/nrftw/Main.less
badd +27 src/main/scala/com/tewe/nrftw/ItemState.scala
badd +11 .metals/readonly/dependencies/laminar_sjs1_3-17.2.0-sources.jar/com/raquo/laminar/api/AirstreamAliases.scala
badd +26 .metals/readonly/dependencies/airstream_sjs1_3-17.2.0-sources.jar/com/raquo/airstream/core/Signal.scala
badd +18 .metals/readonly/dependencies/scalajs-dom_sjs1_3-2.8.0-sources.jar/org/scalajs/dom/URLSearchParams.scala
badd +2 index.js
badd +42 vite.config.js
badd +48 src/main/scala/com/tewe/nrftw/RingBuilder.scala
badd +1 src/main/scala/com/tewe/nrftw/Modal.scala
badd +144 .metals/readonly/dependencies/laminar_sjs1_3-17.2.0-sources.jar/com/raquo/laminar/defs/attrs/HtmlAttrs.scala
badd +37 Session.vim
badd +66 src/main/scala/com/tewe/nrftw/EnchantmentsBuilder.scala
badd +51 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/Product.scala
badd +222 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/collection/Iterable.scala
badd +153 src/main/scala/com/tewe/nrftw/StatsBuilder.scala
badd +25 src/main/scala/com/tewe/nrftw/GemsBuilder.scala
badd +38 src/main/scala/com/tewe/nrftw/Errors.scala
badd +38 src/main/scala/com/tewe/nrftw/EnchantmentsBuilder.css
badd +65 src/main/scala/com/tewe/nrftw/StatsBuilder.css
badd +14 src/main/scala/com/tewe/nrftw/GemsBuilder.css
badd +50 src/main/scala/com/tewe/nrftw/ItemBuilder.css
badd +54 src/main/scala/com/tewe/nrftw/RingBuilder.css
badd +503 .metals/readonly/dependencies/laminar_sjs1_3-17.2.0-sources.jar/com/raquo/laminar/defs/tags/HtmlTags.scala
badd +1 jar:file:///home/tewe/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.5.2/scala3-library_3-3.5.2-sources.jar\!/scala/runtime/stdLibPatches/Predef.scala
badd +342 .metals/readonly/dependencies/airstream_sjs1_3-17.2.0-sources.jar/com/raquo/airstream/state/Var.scala
badd +144 .metals/readonly/dependencies/laminar_sjs1_3-17.2.0-sources.jar/com/raquo/laminar/api/Implicits.scala
badd +9 src/main/scala/com/tewe/nrftw/CompactComponent.scala
badd +41 src/main/scala/com/tewe/nrftw/RunesBuilder.scala
badd +12 src/main/scala/com/tewe/nrftw/RunesBuilder.css
badd +1331 .metals/readonly/dependencies/scala-library-2.13.14-sources.jar/scala/collection/IterableOnce.scala
argglobal
%argdel
$argadd ~/projects/scala-projects/nrftw-toolkit
edit src/main/scala/com/tewe/nrftw/WeaponBuilder.scala
argglobal
balt src/main/scala/com/tewe/nrftw/RunesBuilder.scala
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 1 - ((0 * winheight(0) + 21) / 43)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 1
normal! 0
lcd ~/projects/scala-projects/nrftw-toolkit
tabnext 1
if exists('s:wipebuf') && len(win_findbuf(s:wipebuf)) == 0 && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20
let &shortmess = s:shortmess_save
let s:sx = expand("<sfile>:p:r")."x.vim"
if filereadable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &g:so = s:so_save | let &g:siso = s:siso_save
set hlsearch
let g:this_session = v:this_session
let g:this_obsession = v:this_session
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
