package usingmarkdown4j;

/**
 * An interface for emitting span elements. Currently only used for special
 * links.
 * 
 * @author René Jeschke (rene_jeschke@yahoo.de)
 */
public interface SpanEmitter
{
    /**
     * Emits a span element.
     * 
     * @param out
     *            The StringBuilder to append to.
     * @param content
     *            The span's content.
     */
    public void emitSpan(StringBuilder out, String content);
}
