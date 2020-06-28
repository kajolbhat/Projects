open Stdlib

let _  = Random.self_init ()

type term =
  | Constant of string
  | Variable of string
  | Function of string * term list

type head = term
type body = term list

type clause = Fact of head | Rule of head * body

type program = clause list

type goal = term list

let rec string_of_f_list f tl =
  let _, s = List.fold_left (fun (first, s) t ->
    let prefix = if first then "" else s ^ ", " in
    false, prefix ^ (f t)) (true,"") tl
  in
  s

let rec string_of_term t =
  match t with
  | Constant c -> c
  | Variable v -> v
  | Function (f,tl) ->
      f ^ "(" ^ (string_of_f_list string_of_term tl) ^ ")"

let string_of_term_list fl =
  string_of_f_list string_of_term fl

let string_of_goal g =
  "?- " ^ (string_of_term_list g)

let string_of_clause c =
  match c with
  | Fact f -> string_of_term f ^ "."
  | Rule (h,b) -> string_of_term h ^ " :- " ^ (string_of_term_list b) ^ "."

let string_of_program p =
  let rec loop p acc =
    match p with
    | [] -> acc
    | [c] -> acc ^ (string_of_clause c)
    | c::t ->  loop t (acc ^ (string_of_clause c) ^ "\n")
  in loop p ""

let var v = Variable v
let const c = Constant c
let func f l = Function (f,l)
let fact f = Fact f
let rule h b = Rule (h,b)

(* Problem 1 *)

let rec occurs_check v t =

 (*match t with
  | Variable var -> 
    let str_T = string_of_term t in
    let str_v = string_of_term v in
    if (str_v = str_T) then true else false
  | Function (f,tl) -> 
    let i = 0 in
    let len = List.length(tl)-1 in
    let str_tl = string_of_term (List.hd(tl)) in
    let str_v = string_of_term v in
    if str_v = str_tl then true else false;;*)


    match t with
  | Constant c -> false
  | Variable var -> 
    let str_T = string_of_term t in
    let str_v = string_of_term v in
    if (str_v = str_T) then true else false
  | Function (f,tl) -> 
    let str_v = string_of_term v in
    let str_tl = string_of_term (List.hd(tl)) in
    List.mem v tl;;


    
  (*raise (Failure "Problem 1 Not implemented")*)

(* Problem 2 *)

module VarSet = Set.Make(struct type t = term let compare = Stdlib.compare end)
(* API Docs for Set : https://caml.inria.fr/pub/docs/manual-ocaml/libref/Set.S.html *)

let rec variables_of_term t =

  match t with
  | Constant c -> VarSet.empty
  | Variable x -> VarSet.singleton t
  | Function (f, l) ->
    let e_set = VarSet.empty in
     List.fold_left (fun acc t' ->
       VarSet.union acc (variables_of_term t')
     ) e_set l;;



  (*raise (Failure "Problem 2 Not implemented")*)


let variables_of_clause c =
  match c with
  | Fact f -> variables_of_term f
  | Rule (h,b) -> 
    (match h with
    | Constant c -> VarSet.empty
    | Variable x -> VarSet.singleton h
    | Function(f,l) -> 
      let e_set = VarSet.empty in
      (List.fold_left (fun acc t' ->
       VarSet.union acc (variables_of_term h)
     ) e_set) l);;
    

    

  (*raise (Failure "Problem 2 Not implemented")*)

(* Problem 3 *)

module Substitution = Map.Make(struct type t = term let compare = Stdlib.compare end)
(* See API docs for OCaml Map: https://caml.inria.fr/pub/docs/manual-ocaml/libref/Map.S.html *)

let string_of_substitution s =
  "{" ^ (
    Substitution.fold (
      fun v t s ->
        match v with
        | Variable v -> s ^ "; " ^ v ^ " -> " ^ (string_of_term t)
        | Constant _ -> assert false (* substitution maps a variable to a term *)
        | Function _ -> assert false (* substitution maps a variable to a term *)
    ) s ""
  ) ^ "}"



let rec substitute_in_term s t = 
(* if the find equals the term then thats the answer other wise do nothing)*)
match t with
| Constant c -> t
| Variable v ->
  let a = (Substitution.find_opt t s) in
  (match a with
  | None -> t
  | Some t' -> t')
(* to substitute by iterating through list*)
| Function (f,l) -> Function (f, List.map (substitute_in_term s) l);;

  (*raise (Failure "Problem 3 Not implemented")*)

let substitute_in_clause s c =

let sr = Substitution.empty in

(*(match h with
    | Constant c -> VarSet.empty
    | Variable x -> VarSet.singleton h
    | Function(f,l) -> (List.fold_left (fun acc t' ->
       VarSet.union acc (variables_of_term h)
     ) VarSet.empty) l);;)*)

match c with
| Fact f -> Fact (substitute_in_term s f)
| Rule(h,b) -> Rule (substitute_in_term s h, b);;
  


  (*raise (Failure "Problem 3 Not implemented")*)

(* Problem 4 *)

let counter = ref 0
let fresh () =
  let c = !counter in
  counter := !counter + 1;
  Variable ("_G" ^ string_of_int c)

let freshen c =
  let vars = variables_of_clause c in
  let s = VarSet.fold (fun v s -> Substitution.add v (fresh()) s) vars Substitution.empty in
  substitute_in_clause s c

(*
let c = (rule (func "p" [var "X"; var "Y"; const "a"]) [func "q" [var "X"; const "b"; const "a"]])
let _ = print_endline (string_of_clause c)
let _ = print_endline (string_of_clause (freshen c))
*)

exception Not_unifiable

let rec unify_helper t1 t2 s = 
(*match (x,y) with
| Constant c1, Constant c2 ->
  if (c1=c2) then s else ()
| (Variable v1, Variable v2) ->  
  if (occurs_check x y <> true) then Substitution.add x y (Substitution.empty)
  else if (occurs_check v2 v1 <> true) then Substitution.add v2 v1 Substitution.empty
  else s
| (Function (f1,l1), Function (f2,l2)) -> List.fold_left2(fun s(f1, f2) -> unify_helper(f1,f2) s);;*)

let t1 = substitute_in_term s t1 in
let t2 = substitute_in_term s t2 in

match t1,t2 with
| (Variable x, _) -> 
  let a = string_of_term t1 in
  let b = string_of_term t2 in
  if a = b then s
  else if (occurs_check t1 t2) then raise Not_unifiable
  else 
  let s_s = Substitution.singleton t1 t2 in
  let m_s = Substitution.map(fun f-> substitute_in_term s_s f) s in
  Substitution.add t1 t2 m_s
| (_, Variable x) -> 
  if t1=t2 then s
  else if (occurs_check t2 t1) then raise Not_unifiable
  else
  let s_s = Substitution.singleton t2 t1 in
  let m_s = Substitution.map(fun f-> substitute_in_term s_s f) s in
  Substitution.add t2 t1 m_s
| Function (h1,b1), Function (h2,b2) ->
  let lenb1 = List.length b1 in
  let lenb2 = List.length b2 in
  if (h1=h2) && (lenb1 = lenb2) then
  List.fold_left2 (fun s t1 t2 -> unify_helper t1 t2 s) s b1 b2
  else raise Not_unifiable
| (Constant c1, Constant c2) -> 
  if t1=t2 then Substitution.empty else raise Not_unifiable
| _ -> raise Not_unifiable;;




let unify t1 t2 =
let s = Substitution.empty in unify_helper t1 t2 s;;



  (*raise (Failure "Problem 4 Not implemented")*)

(* Problem 5 *)

let rec nondet_helper_w program goal res = 
match res with
| [] -> goal
| res ->
  let ran = Random.int (List.length res) in
  let a = List.nth res ran in
  let ran_num = Random.int (List.length program) in
  let a' = freshen (List.nth program ran_num) in
  let rs = List.filter (fun f -> f<>a) res in
  (*rs is remaining list with removed value*)
  let f_a' = freshen a' in 

  (match (f_a') with
  | Fact(head) ->
    let hs = Substitution.empty in
    (match unify head a with
    | exception Not_unifiable -> goal
    | s ->
    let g = Substitution.map(fun f-> substitute_in_term hs f) hs in
    let goal' = List.map(fun f -> substitute_in_term g f) goal in
    let res' = List.map(fun f -> substitute_in_term g f) rs in
    nondet_helper_w program goal' res')

  | Rule (head, body) ->
    let hs = unify head a in
    (match unify head a with
    | exception Not_unifiable -> goal
    | s ->
    let g = Substitution.map(fun f-> substitute_in_term hs f) hs in
    let goal' = List.map(fun f -> substitute_in_term g f) goal in
    let res' = List.map(fun f -> substitute_in_term g f) rs in
    nondet_helper_w program goal' res'));;
    

  


let nondet_query program goal =
let (res: term list) = goal in
if (List.length res > 0) then (nondet_helper_w program goal res)
else if (res = []) then nondet_helper_w program goal res else goal;;

(*raise (Failure "Problem 5 Not implemented")*)

(* Problem Bonus *)

let det_query program goal =
  raise (Failure "Problem Bonus Not implemented")
