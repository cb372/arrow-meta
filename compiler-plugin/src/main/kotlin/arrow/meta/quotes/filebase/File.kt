package arrow.meta.quotes.filebase

import arrow.meta.quotes.Scope
import arrow.meta.quotes.ScopedList
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
import org.jetbrains.kotlin.com.intellij.psi.PsiClass
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFileAnnotationList
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtImportList
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.KtScript
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub

/**
 * <code>file(name, """ $packageDirective $importList $classes """)</code>
 *
 * A template destructuring [Scope] for a [File]. See below:
 *
 * ```
 * import arrow.meta.Meta
 * import arrow.meta.Plugin
 * import arrow.meta.invoke
 * import arrow.meta.quotes.Transform
 * import arrow.meta.quotes.file
 *
 * val Meta.reformatFile: Plugin
 *   get() =
 *     "ReformatFile" {
 *       meta(
 *        file({ true }) { f ->
 *          Transform.replace(
 *            replacing = f,
 *            newDeclaration = {
 *               """ $packageDirective $importList $classes """.file(name)
 *            }
 *          )
 *        }
 *       )
 *     }
 * ```
 */
class File(
  override val value: KtFile?,
  val `@annotations`: ScopedList<KtAnnotationEntry> = ScopedList(value?.annotationEntries ?: listOf()),
  val name: String? = value?.name,
  val importList: Scope<KtImportList> = Scope(value?.importList), // TODO KtImportList scope and quote template
  val fileAnnotationList: Scope<KtFileAnnotationList> = Scope(value?.fileAnnotationList), // TODO KtFileAnnotationList scope and quote template
  val importDirectives: ScopedList<KtImportDirective> = ScopedList(value = value?.importDirectives ?: listOf(), postfix = ", "),
  val packageDirective: Scope<KtPackageDirective> = Scope(value?.packageDirective), // TODO KtPackageDirective scope and quote template
  val packageFqName: FqName? = value?.packageFqName,
  val packageFqNameByTree: FqName? = value?.packageFqNameByTree,
  val script: Scope<KtScript>? = Scope(value?.script), // TODO KtScript scope and quote template
  val danglingAnnotations: ScopedList<KtAnnotationEntry> = ScopedList(value = value?.danglingAnnotations ?: listOf(), postfix = ", "),
  val fileType: FileType? = value?.fileType,
  val declarations: ScopedList<KtDeclaration> = ScopedList(value = value?.declarations ?: listOf(), postfix = ", "),
  val stub: KotlinFileStub? = value?.stub,
  val classes: Array<PsiClass> = value?.classes ?: arrayOf()
): Scope<KtFile>(value)